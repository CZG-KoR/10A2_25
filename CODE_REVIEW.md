# Code Review Report – Tower Defense Game

**Date:** 2026-05-04
**Reviewer:** Claude Code
**Scope:** All Java source files under `src/main/java/`

---

## Summary

The codebase has several **compilation blockers** that prevent the project from building at all, plus a number of **logic bugs** that would cause incorrect behaviour at runtime, and a few **architectural issues** worth addressing.

---

## 1. Compilation Errors

### 1.1 Extra Closing Brace
**Files:** `Tower.java:118`, `Tower2.java:78`, `Tower3.java:108`, `Mouse.java:112`

Each of these files has a stray `}` after the class's own closing brace, producing a syntax error.

**Fix:** Delete the last `}` in each affected file.

---

### 1.2 Missing Import in `Ticks.java`
**File:** `gamelogic/Ticks.java:12`

```java
private lilC c;   // 'lilC' is in package 'main', but not imported
```

**Fix:** Add `import main.lilC;` at the top of the file.

---

### 1.3 Static Method Accesses Instance Field `u`
**Files:** `Tower.java:104`, `Tower2.java:64`, `Tower3.java:93`

```java
static public void place(int x, int y) {
    if (gamelogic.Shop.money >= u[0][0] ...)   // 'u' is an instance field
```

Each tower subclass declares `int[][] u` as an **instance** field, but `place()` is `static`. Java will not compile a static reference to a non-static field, even if a static field with the same name exists in the parent class.

**Fix:** Remove the `int[][] u` instance field redeclarations from all three Tower subclasses. The already-defined `protected static int[][] u` in `AbstractTower` is inherited and accessible in static context via `AbstractTower.u`.

---

## 2. Logic Bugs

### 2.1 Missing `break` in `waves()` Switch — Wave 1 Falls Through to Wave 2
**File:** `Main.java:701`

```java
case 1:
    // spawns enemies …
    // ← NO break here
case 2:
    // wave 1 enemies are spawned, then wave 2 enemies are also spawned
    break;
```

When wave 1 is started, Java falls through and also executes the `case 2` block, spawning both waves' enemies at once.

**Fix:** Add `break;` after the last statement of `case 1`.

---

### 2.2 Enemy Bounty is Never Awarded
**File:** `Enemy.java:115`

```java
public int die() {
    // removes enemy from list…
    return bounty;   // return value is never used by callers
}
```

`takeDamage()` calls `this.die()` but discards the return value, so players never earn money for killing enemies.

**Fix:** In `takeDamage()`, add `gamelogic.Shop.money += this.die();` (or `+= bounty` directly before calling `die()`).

---

### 2.3 Skip-on-Removal Bug in `die()` and `doDamage()`
**File:** `Enemy.java:117–157`

```java
for (int i = 0; i < Standard.size(); i++) {
    if (Standard.get(i).healthpoints <= 0) {
        Standard.remove(i);   // element at i+1 slides to i, then i++ skips it
    }
}
```

After removing element `i`, the next element shifts down to index `i`, but the loop increments `i` anyway, silently skipping it. Dead enemies survive one extra tick.

**Fix:** Iterate backwards:
```java
for (int i = Standard.size() - 1; i >= 0; i--) {
    if (Standard.get(i).healthpoints <= 0) Standard.remove(i);
}
```

---

### 2.4 `AbstractTower.enemy` is Always `null` → NullPointerException
**File:** `Enemy.java:14`, `AbstractTower.java:10`, `Tower.java:28`

```java
// Enemy.java
static Enemy Enemy;           // never assigned → always null

// AbstractTower.java
protected Enemy enemy = Enemy.Enemy;   // always null
```

`Tower.farestEnemy()` returns `enemy` (null) when no enemy is found in range. `shoot()` then calls `en.takeDamage(...)` on the null reference.

**Fix:** Change `farestEnemy()` to return `null` explicitly and guard in `shoot()`:
```java
public void shoot(AbstractTower tower) {
    if (Enemyinrange(tower)) {
        Enemy en = farestEnemy(tower);
        if (en == null) return;
        // … damage logic
    }
}
```
Also remove the now-pointless `static Enemy Enemy;` field from `Enemy.java` and the `protected Enemy enemy` field from `AbstractTower`.

---

### 2.5 Tower Added to `allTowers` Twice
**File:** `Tower.java:111`

```java
static public void place(int x, int y) {
    Tower k = new Tower(…);    // AbstractTower constructor: allTowers.add(this)
    …
    Towers1.add(k);
    allTowers.add(k);           // added a second time
}
```

`AbstractTower`'s constructor already calls `allTowers.add(this)`. The explicit `allTowers.add(k)` in `Tower.place()` duplicates the entry, making every Tower1 shoot twice per tick and appear twice in upgrade/freeze loops.

**Fix:** Remove `allTowers.add(k);` from `Tower.place()`. (Tower2 and Tower3 do not have this problem.)

---

### 2.6 Enemy-Search Loops Only Cover a 10×10 Portion of the Map
**Files:** `Tower.java:36–37`, `Tower3.java:36–37`

```java
for (int k = 0; k < 10; k++) {       // should be < 35
    for (int l = 0; l < 10; l++) {    // should be < 25
        if (main.Main.lilM[k][l]…)
```

`lilM` is declared as `Tiles[35][25]`. Towers only inspect the top-left 10×10 tile region; enemies on any tile with column ≥ 10 or row ≥ 10 are invisible to towers.

**Fix:** Change the loop bounds to `k < 35` and `l < 25`.

---

### 2.7 `upgradefreeze` Condition is Backwards
**File:** `AbstractTower.java:109`

```java
if (t.modef == 1 && Math.sqrt(…) >= dist) {
    g = true;   // g = true when a freeze tower IS far away
}
if (g == false) {
    tower.modef = 1;   // only allows upgrade when NO freeze tower is far away
}
```

The intent seems to be: *"don't allow a freeze upgrade if there is already one nearby."* But the `>=` condition makes `g = true` whenever an existing freeze tower is **far away**, which is almost always true → freeze upgrades are permanently blocked once any freeze tower exists anywhere on the map.

**Fix:** Change `>= dist` to `<= dist` (block if an existing freeze tower is *within* dist).

---

### 2.8 Wave 5 Spawns Zero Enemies
**File:** `Main.java:745`

```java
case 5:
    for (int i = 0; i < 0; i++) {   // loop body never executes
```

An empty wave immediately triggers `die()`'s empty-list check, which calls `waves(6)` — wave 5 is silently skipped.

**Fix:** Change `0` to the intended enemy count (e.g. `for (int i = 0; i < 8; i++)`).

---

### 2.9 `Mouse` is Never Registered as a Listener
**File:** `gamelogic/Mouse.java`

`Mouse` defines `mouseClicked(MouseEvent e)` but does not implement `java.awt.event.MouseListener` and is never added to the `JFrame` or `JPanel`. No click events are processed.

**Fix:**
1. Add `implements MouseListener` to the class declaration.
2. Implement the other four required stub methods (`mousePressed`, `mouseReleased`, `mouseEntered`, `mouseExited`).
3. In `Main.main()`, register it: `f.addMouseListener(new Mouse());`

---

### 2.10 Range Comparison Uses Tile Units vs. Pixel Coordinates
**File:** `AbstractTower.java:46–47`, similar in `Tower.java:40`

```java
Math.sqrt((e.getX() - tower.getX())^2 + (e.getY() - tower.getY())^2) <= tower.range
```

`tower.range` is `3` (tile units), but `getX()` / `getY()` return **pixel** coordinates (tiles are 40 px each). A range of `3` pixels is essentially zero — towers will almost never fire.

**Fix:** Multiply `tower.range` by the tile size when comparing:
```java
… <= tower.range * 40
```

---

### 2.11 `shootfunction()` Intercept Formula is Incorrect and Unused
**File:** `AbstractTower.java:137`

```java
f[1] = (double)(y1 - y2 - x2) / (x1 - x2) + y2;   // wrong formula
```

The standard y-intercept `b = y1 - slope * x1`. The current formula includes `- x2` in the numerator, which is dimensionally wrong (mixing x-coordinate into a y calculation). Additionally, `shootfunction()` is **never called** anywhere — no projectile is drawn or simulated.

**Fix:** Either correct the formula to `f[1] = y1 - f[0] * x1;` and wire it into a projectile system, or remove the method if projectile rendering is not planned.

---

### 2.12 Bitwise `&` Instead of Logical `&&` in Coordinate Comparison
**Files:** `Tower.java:45,51,57`, `Tower3.java:45,50,55`

```java
if (e.getX() == xt & e.getY() == yt)
```

For `boolean` operands the result is the same, but `&` does not short-circuit — the right side always evaluates. This is misleading and can hide intent. Replace all occurrences with `&&`.

---

## 3. Architectural Issues

### 3.1 Global Static State With No Reset Path
`Shop.money/flowers/mango`, `Enemy.Standard/Fast/Tank`, and `AbstractTower.allTowers` are all static. If the player loses and the game restarts, stale enemies and towers remain. A future fix is to add a `reset()` method that clears all these fields.

### 3.2 `Mouse` Not Implementing `MouseListener` (see 2.9)
The entire interaction system is non-functional until this is addressed.

### 3.3 `allenemies` in `Main` is Unused
`public static ArrayList<Enemy> allenemies` is declared but never populated or referenced. Remove it to avoid confusion.

### 3.4 `Terrain.java` is an Empty Placeholder
The class has no content. Either implement it or delete it to avoid noise.

### 3.5 `Ticks` Fires Before `JFrame` is Visible
In `Main.main()`, `new Ticks(c)` is called **before** `f.setVisible(true)`, meaning enemy timers start before the window appears. This is a race but harmless in practice on the EDT. Consider calling `new Ticks(c)` after `f.setVisible(true)`.

---

## Priority Fix Order

| # | Issue | Impact |
|---|-------|--------|
| 1 | Extra closing braces (1.1) | Won't compile |
| 2 | Missing `lilC` import (1.2) | Won't compile |
| 3 | Static method accesses instance field `u` (1.3) | Won't compile |
| 4 | `Mouse` not registered (2.9) | No interaction |
| 5 | Range uses tile units, not pixels (2.10) | Towers never shoot |
| 6 | `allTowers` double-add for Tower1 (2.5) | Double damage/freeze |
| 7 | Bounty never awarded (2.2) | No economy |
| 8 | Missing `break` in wave switch (2.1) | Wave 1 spawns double |
| 9 | Null enemy returned → NPE (2.4) | Crash on first shot |
| 10 | Search loop bounds too small (2.6) | Towers ignore 75 % of map |
| 11 | `upgradefreeze` condition backwards (2.7) | Freeze upgrade blocked |
| 12 | Skip-on-removal in `die()` (2.3) | Dead enemies linger |
| 13 | Wave 5 empty (2.8) | Wave skipped silently |

---

## Follow-Up Review — 2026-05-04

This section documents the status of all previous findings and adds newly discovered issues found in the current source.

---

### Status of Previous Issues

| # | Issue | Status |
|---|-------|--------|
| 1.1 | Extra closing braces | **Fixed** |
| 1.2 | Missing `lilC` import in `Ticks.java` | **Fixed** |
| 1.3 | Static `place()` accessing instance field `u` | **Fixed** |
| 2.1 | Missing `break` after `case 1` in `waves()` | **Fixed** |
| 2.2 | Bounty never awarded | **Fixed** — `die()` now adds bounty to `Shop.money` |
| 2.3 | Skip-on-removal in `die()` | **Fixed** — now iterates backwards |
| 2.4 | `AbstractTower.enemy` always `null` → NPE | **Partially fixed** — `Enemy.Enemy` is now initialised to a dummy instance, preventing a NullPointerException, but the dummy sentinel introduces its own bug (see N4 below) |
| 2.5 | `allTowers` double-add for Tower1 | **Fixed** |
| 2.6 | Enemy-search loop bounds `< 10` | **Fixed** — bounds are now `< 35` / `< 25` |
| 2.7 | `upgradefreeze` condition `>= dist` (backwards) | **Not fixed** — `AbstractTower.java:110` still uses `>= dist` |
| 2.8 | Wave 5 spawns zero enemies | **Fixed** |
| 2.9 | `Mouse` not registered as a listener | **Fixed** — `Mouse` now implements `MouseListener`; registered via `c.addMouseListener(mouse)` in `Main.java:33` |
| 2.10 | Range compared in tile units against pixel coords | **Not fixed** — all `<= tower.range` checks still use raw tile-unit value `3` |
| 2.11 | `shootfunction()` formula wrong and never called | **Partially fixed** — formula is now correct (`f[1] = y1 - f[0] * x1`), but the method is **still never called** |
| 2.12 | Bitwise `&` instead of `&&` | **Not fixed** — `Tower.java:47,53,59` and `Tower3.java:47,52,57` still use `&` |
| 3.1 | Global static state, no reset path | **Not fixed** |
| 3.3 | `allenemies` unused | **Not fixed** — `Main.java:25` still declares the dead field |
| 3.4 | `Terrain.java` empty | **Not fixed** |
| 3.5 | `Ticks` fires before `JFrame` is visible | **Not fixed** |

---

## 4. New Compilation Errors

### 4.1 `@Override getTargetEnemy()` in `Tower3` Has No Parent Declaration
**File:** `Tower3.java:13`

```java
@Override
public Enemy getTargetEnemy() { … }
```

`AbstractTower` does not declare `getTargetEnemy()`. The `@Override` annotation causes a compile-time error: *"method does not override or implement a method from a supertype."*

**Fix:** Either declare `public abstract Enemy getTargetEnemy()` in `AbstractTower` (and implement it in Tower and Tower2), or remove `@Override` from Tower3.

---

### 4.2 Type Mismatch in `Tower.shoot()` — `AbstractTower` Passed to `farestEnemy(Tower)`
**File:** `Tower.java:87`

```java
public void shoot(AbstractTower tower) {
    …
    Enemy en = farestEnemy(tower);   // farestEnemy(Tower tower) — wrong type
```

`farestEnemy` is declared as `public Enemy farestEnemy(Tower tower)`. Inside `shoot(AbstractTower tower)`, `tower` has the declared type `AbstractTower`, which is a supertype of `Tower`. Java will not implicitly narrow the reference; this is a compile-time type error.

**Fix:** Cast the parameter: `Enemy en = farestEnemy((Tower) tower);`  (safe because `shoot` is only ever dispatched on a `Tower` instance).

---

## 5. New Logic Bugs

### 5.1 First Upgrade *Decreases* Tower Damage
**File:** `AbstractTower.java:81–87`, `Tower.java:14`, `Tower2.java:8`, `Tower3.java:11`

The `u` upgrade table in every tower subclass is:
```java
{ { 500, 3, 10, 2, 3 },   // loaded after level 0 → 1 upgrade
  { 600, 3, 20, 4, 4 },   // loaded after level 1 → 2 upgrade
  { 1000, 4, 40, 5, 7 } } // loaded after level 2 → 3 upgrade
```

The constructor sets `this.damage = 20`. After the first upgrade `u[0][2] = 10` is applied — damage **drops from 20 to 10**. Only after the second upgrade does it recover to 20. This is almost certainly unintentional.

**Fix:** Either swap the row order in `u` so the weakest values are not the level-0→1 result, or adjust the indexing in `upgrade()` to use `tower.level + 1` as the row index.

---

### 5.2 Tower Placed at Raw Click Coordinates, Not Tile-Snapped
**File:** `Mouse.java:108–119`, `Tower.java:110`, `Tower2.java:75`, `Tower3.java:103`

`placing()` correctly identifies the clicked tile via `tX = x / 40` and `tY = y / 40`, but then passes the raw pixel coordinates to `Tower.place(x, y)`. The tower is therefore stored at an arbitrary sub-tile pixel position. The `farestEnemy` / `farestEnemys` searches compare enemy positions with tile coordinates using exact equality (`e.getX() == xt`), so a tower placed at (163, 247) will never match any tile centre. Additionally, towers appear misaligned visually.

**Fix:** Pass tile-snapped coordinates: `Tower.place(tX * 40, tY * 40)` (same for Tower2/Tower3).

---

### 5.3 Initial Tower Image Never Found — Tower Renders Invisible
**File:** `AbstractTower.java:94`, initial `level = 0`

`updateImage()` builds the path `/Bilder/Koala<type>LVL<level>.png`. At placement time `level = 0`, producing e.g. `Koala1LVL0.png`. No such file exists; the resource URL is `null`, the error is printed to stderr, and the image remains `null`. `lilC.paintComponent()` already guards with `if (t.getImg() != null)`, so towers are simply invisible until their first upgrade.

Available images start at `LVL1`. The level counter should start at `1`, or the path should use `level + 1`.

---

### 5.4 Dummy Enemy Sentinel Can Spuriously Advance the Wave Counter
**File:** `AbstractTower.java:10`, `Tower.java:29–30`, `Enemy.java:14`

```java
// AbstractTower.java
protected Enemy enemy = Enemy.Enemy;   // the global dummy

// Tower.farestEnemy — returns the dummy when no enemy is found
Enemy en = enemy;
…
return en;
```

If `Enemyinrange()` returns `true` but `farestEnemy()` finds no matching enemy on any tile (possible if range bug 2.10 is fixed or timing races occur), `shoot()` calls `en.takeDamage(damage)` on the dummy. After enough hits, the dummy's `healthpoints` reach `≤ 0` and `die()` is called. `die()` iterates Standard/Tank/Fast, removes nothing (dummy is in none of them), then checks:

```java
if (Standard.isEmpty() && Fast.isEmpty() && Tank.isEmpty()) {
    Main.wave++;
    Main.waves(Main.wave);
}
```

If all enemy lists happen to be empty (e.g., between waves), this incorrectly triggers the next wave, skipping the intended inter-wave pause.

**Fix:** Guard `shoot()` against the sentinel return value, and explicitly return `null` from `farestEnemy()` when no real enemy is found:
```java
Enemy en = farestEnemy(tower);
if (en == null) return;
```

---

### 5.5 `doDamage()` Loss-Clearing Loops Still Have Skip-on-Removal Bug
**File:** `Enemy.java:154–161`

The game-over clearing loops use forward iteration with concurrent removal (the same pattern fixed in `die()`):

```java
for (int i = 0; i < Standard.size(); i++) {
    Standard.remove(i);   // shifts all subsequent elements; i++ then skips one
}
```

Only approximately half the enemies are removed. The remainder continue to update, reach the exit, and call `doDamage()` again (printing "You Lose!" repeatedly and resetting `wave = 0` each time).

**Fix:** Iterate backwards (same as the corrected `die()` loops), or simply call `Standard.clear()`.

---

### 5.6 No Win Condition After Wave 10
**File:** `Main.java:691`, `Enemy.java:136–139`

After the last enemy of wave 10 is killed, `die()` increments `Main.wave` to `11` and calls `waves(11)`. There is no `case 11` in the switch — the method returns without adding any enemies. Since the lists are now empty, `die()` is never triggered again. The game silently stalls with no "You Win!" message or end screen.

**Fix:** Add a `default` or explicit `case` after wave 10 that displays a win screen and halts the timers.

---

### 5.7 No Actual Game-Over State — Game Continues After Health Reaches Zero
**File:** `Enemy.java:149–166`

`doDamage()` prints `"You Lose!"`, resets `wave = 0`, and tries (incompletely — see 5.5) to clear the enemy lists. No timers are stopped, no flag is set to prevent further gameplay. Enemies left alive by the buggy clear continue to move and call `doDamage()`, printing "You Lose!" every tick until removed.

---

### 5.8 `takeDamageandFreeze()` Sets `freeze` After the Enemy Has Died
**File:** `Enemy.java:104–113`

```java
public void takeDamageandFreeze(int damage) {
    this.healthpoints -= damage;
    if (healthpoints <= 0) {
        this.die();       // enemy removed from list here
    }
    this.freeze = (int) Math.round(r.nextGaussian(50, 25));  // runs even after die()
}
```

`die()` returns normally after removing `this` from its list. Execution then continues and writes to `this.freeze` on the now-deallocated (logically removed) object. While this doesn't crash, the Gaussian RNG call is wasted, and if any other code holds a reference to the enemy it will see a non-zero `freeze` value.

**Fix:** Return immediately after `this.die()`.

---

### 5.9 Enemy Update Timers Can Skip Enemies When `die()` Removes During Iteration
**File:** `Ticks.java:18–32`

```java
Timer EStandard = new Timer(9, (ActionEvent e) -> {
    for (int i = 0; i < Enemy.Standard.size(); i++) {
        Enemy.Standard.get(i).update();   // may call die() → modifies Standard
    }
```

When `update()` triggers `doDamage()` → `die()`, elements are removed from `Enemy.Standard` mid-loop. The forward-index loop then increments `i` over the newly shifted element, silently skipping one enemy per tick. (Note: the `TowerShoot` timer already uses a defensive copy and is unaffected.)

**Fix:** Either iterate a defensive copy (`new ArrayList<>(Enemy.Standard)`) or iterate backwards.

---

### 5.10 `Boss` Constructor Assigns `velocity` Parameter to the `freeze` Field
**File:** `Boss.java:17–22`

```java
public Boss(int damage, int velocity, int healthpoints, int bounty, …) {
    …
    this.freeze = velocity;   // 'velocity' stored as freeze countdown
}
```

`freeze` in `Enemy` is a cooldown tick-counter that slows movement, not a velocity field. Passing a movement speed to this constructor silently stores it as a freeze duration, making the Boss move at full speed for `velocity` fewer ticks rather than moving at `velocity` units/tick.

Additionally, no `Boss` enemies are ever spawned by `waves()`, and `die()` has no special handling for Boss flower bounty, so `bountyFlower` is never awarded.

---

### 5.11 `Boss` Flower Bounty Never Awarded
**File:** `Boss.java:9`, `Enemy.java:115–140`

`Boss` declares `int bountyFlower` and a getter, but `die()` in `Enemy` only awards `Shop.money += bounty`. No code ever increments `Shop.flowers` or `Shop.mango` upon a Boss kill. Even if Bosses were spawned, players would receive no flower reward.

---

### 5.12 `Shop` Constructor Modifies Static Fields — Singleton Violation
**File:** `Shop.java:12–15`

```java
public Shop(int money, int flowers) {
    Shop.money = money;     // modifies global state
    Shop.flowers = flowers;
}
```

Creating any `Shop` instance with non-default arguments silently overwrites the global currency for all players/towers. No `Shop` instance is ever created in the current code, but the constructor is a trap for future developers. Also there is no `mango` parameter, so a new `Shop` can never initialise `mango`.

---

## Priority Fix Order (Updated)

| # | Issue | Impact |
|---|-------|--------|
| 1 | `@Override getTargetEnemy()` missing in AbstractTower (4.1) | Won't compile |
| 2 | Type mismatch in `Tower.shoot()` (4.2) | Won't compile |
| 3 | `upgradefreeze` condition still backwards (2.7) | Freeze upgrade permanently blocked |
| 4 | Range still in tile units (2.10) | Towers never fire |
| 5 | Tower placed at raw pixel coords (5.2) | Towers can't find enemies |
| 6 | Initial tower level 0 → image missing (5.3) | Towers invisible at placement |
| 7 | First upgrade decreases damage (5.1) | Upgrade harms the player |
| 8 | Dummy sentinel advances wave counter (5.4) | Waves skip unexpectedly |
| 9 | `doDamage()` clear loops skip enemies (5.5) | Enemies survive game-over |
| 10 | No game-over state (5.7) | Game never ends on loss |
| 11 | No win condition (5.6) | Game stalls after wave 10 |
| 12 | Enemy timer skip-on-removal (5.9) | Enemies skip a tick on kill |
| 13 | `takeDamageandFreeze()` sets freeze after death (5.8) | Logic error on fatal freeze shot |
| 14 | `Boss` constructor misuses `velocity` (5.10) | Boss speed stored as freeze |
| 15 | Boss bounty flower never awarded (5.11) | Economy dead for Boss kills |
| 16 | Bitwise `&` instead of `&&` (2.12) | Misleading, no short-circuit |
| 17 | `shootfunction()` never called (2.11) | Dead code |
| 18 | `allenemies` unused (3.3) | Dead field |
| 19 | `Shop` constructor modifies statics (5.12) | Future trap |
| 20 | `Terrain.java` empty (3.4) | Noise |
