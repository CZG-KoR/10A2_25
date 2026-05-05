# TODO Fixes — Tower Defense Game

Ordered by recommended fix priority.

---

## 1. Initial tower image missing (Koala1LVL0.png)
**File:** `AbstractTower.java:94`

Images start at LVL1 but `level` starts at 0.

```java
// Change:
String path = "/Bilder/Koala" + getTowerType() + "LVL" + level + ".png";
// To:
String path = "/Bilder/Koala" + getTowerType() + "LVL" + (level + 1) + ".png";
```

---

## 2. Tower placed at raw pixel coordinates instead of tile-snapped
**File:** `Mouse.java:109,113,117`

Enemy search uses exact tile-coordinate equality, so pixel-positioned towers never match.

```java
// Change:
Tower.place(x, y);
Tower2.place(x, y);
Tower3.place(x, y);
// To:
Tower.place(tX * 40, tY * 40);
Tower2.place(tX * 40, tY * 40);
Tower3.place(tX * 40, tY * 40);
```

---

## 3. Range comparison uses tile units instead of pixels
**File:** `AbstractTower.java:47,53,59,70`

`tower.range` is 3 (tiles), but coordinates are in pixels (40px per tile). Towers never fire.

```java
// Change all four occurrences of:
<= tower.range
// To:
<= tower.range * 40
```

---

## 4. No null-guard for dummy enemy in Tower.shoot()
**File:** `Tower.java:86-98`

`farestEnemy()` returns the dummy `Enemy.Enemy` when no real enemy is found. Shooting the dummy can spuriously advance waves.

```java
// After:
Enemy en = farestEnemy((Tower) tower);
// Add:
if (en == Enemy.Enemy) return;
```

---

## 5. takeDamageandFreeze() sets freeze after enemy has died
**File:** `Enemy.java:107-113`

Execution continues after `die()`, setting freeze on a removed enemy.

```java
if (healthpoints <= 0) {
    this.die();
    return;   // <-- add this
}
```

---

## 6. doDamage() loss-clearing loops skip every other enemy
**File:** `Enemy.java:154-161`

Forward iteration with concurrent removal only removes ~half.

```java
// Replace all three forward-removal loops with:
Standard.clear();
Fast.clear();
Tank.clear();
```

---

## 7. Enemy update timers skip enemies when die() removes during iteration
**File:** `Ticks.java` (EStandard, EFast, ETank timers)

Forward-index loop skips an element after removal.

```java
// Iterate a defensive copy:
for (Enemy e : new ArrayList<>(Enemy.Standard)) { e.update(); }
// Same for Fast and Tank
```

---

## 8. upgradefreeze condition is backwards
**File:** `AbstractTower.java:110`

`>= dist` blocks freeze upgrade whenever any freeze tower exists anywhere.

```java
// Change:
>= dist
// To:
<= dist
```

---

## 9. First upgrade decreases tower damage
**File:** `AbstractTower.java:81-87`, `Tower.java:14`, `Tower2.java`, `Tower3.java:11`

Upgrade table row 0 sets damage to 10, down from the initial 20. Reorder the rows so values increase:

```java
// Change:
{ { 500, 3, 10, 2, 3 }, { 600, 3, 20, 4, 4 }, { 1000, 4, 40, 5, 7 } }
// To:
{ { 500, 3, 25, 2, 4 }, { 600, 3, 35, 4, 5 }, { 1000, 4, 50, 5, 7 } }
```

(Adjust values to desired balance — the key point is each row must be stronger than the previous.)

---

## 10. No win condition after wave 10
**File:** `Main.java` in `waves()` switch

Game stalls silently after all wave-10 enemies are killed.

```java
// Add after the last case in the switch:
default:
    if (wave > 10) {
        System.out.println("You Win!");
        // TODO: stop timers / show win screen
    }
    break;
```

---

## 11. No game-over state — timers keep running after health reaches 0
**File:** `Enemy.java:doDamage()`

`doDamage()` prints "You Lose!" but doesn't stop timers or set a flag. Enemies keep moving and dealing damage every tick.

**Fix:** Stop the `Ticks` timers and set a `gameOver` flag that prevents further gameplay.

---

## 12. Bitwise `&` instead of logical `&&`
**Files:** `Tower.java:47,53,59` and `Tower3.java:46,51,56`

```java
// Change:
e.getX() == xt & e.getY() == yt
// To:
e.getX() == xt && e.getY() == yt
```

---

## 13. `allenemies` field is unused
**File:** `Main.java:25`

```java
// Remove:
public static ArrayList<Enemy> allenemies = new ArrayList<>();
```

---

## 14. Boss constructor stores velocity in freeze field
**File:** `Boss.java:17-22`

```java
// Change:
this.freeze = velocity;
// To:
// Store velocity properly (add a velocity field or pass to super)
```

---

## 15. Terrain.java is empty
**File:** `gamelogic/Terrain.java`

Delete the file or implement it.
