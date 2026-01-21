# HappyShop DevLog 

## CI553


---

## Overview 

This log documents the changes and implementation of features made to the HappyShop application. 

## Project Setup & Plan 

**Tasks** 
- Explore the default project structure 
- Identify MVC components 
- Run application to confirm all systems work 

**Notes** 
- Customer functionality is split into MVC structure, in customer folder. 
- Database access is handled via `DatabaseRW` and `DerbyRW`

**Outcome** 
- Ready to begin implementing first feature 

--- 

## Organized Trolley Feature

**Tasks** 
- Implement organized trolley display 
- Merge duplicate products into a single line with quantity 
- Sort trolley items by Product ID (ascending)

**Implementation Details** 
- Changes made in `ProductListFormatter.buildString()`
- Used a 'TreeMap' to group products by Product ID
- Quantities summed before formatting output 
- Existing trolley data structure left unchanged 

**Problems Encountered**
- Initial scope errors due to misplaced loops and variable shadowing 
- Fixed by separating grouping and formatting logic into two loops 

**Outcome** 
- Trolley now displays a clean, readable summary 
- Duplicate items are merged correctly 
- Feature tested successfully

--- 

# Flexible Customer Search (ID + Name)

**Tasks** 
- Implement flexible product search in Customer interface 
- Enable search by Product ID or Product Name using search button 

**Design Decision** 
- UI contains two input fields (ID and Name)
- Search priority: 
  1. Product ID field 
  2. Product Name field 
  3. Prompt user if both are empty 

**Implementation Details** 
- Updated `CustomerModel.search()`
- ID search uses `searchByProductID`
- Name search uses `databaseRW.searchProduct()` (same method as Warehouse)
- Multiple name matches display a short list and prompt refinement 
- Prevented adding products when results are ambiguous or out of stock 

**Problems Encountered** 
- Initial name search did not work due to ID-only logic 
- Resolved by aligning Customer search behaviour with Warehouse search model
- Formatting issues 

**Outcome** 
- Customer search now supports both ID and Name 
- Behaviour matches UI expectations 
- Feature tested with single, multiple, and empty results 

--- 

## Background Sound (Initial Implementation)

**Goal**
- Add continuous background sound that plays while the application is running. 

**Design Decision** 
- Encapsulate audio functionality in a dedicated utility class (`SoundPlay`)
- Initialise audio once during application startup to avoid repeated playback 
- Stop audio when application exits 

**Implementation** 
- Created `SoundPlay` class using JavaFX `Media` and `MediaPlayer`
- Audio file placed in `src/main/resources/audio/`
- Audio initialised in `Main.start()`
- Looping enabled using `MediaPlayer.INDEFINITE`

**Status**
- Code compiles successfully
- Application crashes at runtime due to media initialisation failure

## JavaFX Media Configuration

**Issue** 
- JavaFX media classes were not resolving correctly 

**Investigation**
- Identified that JavaFX Media is a module not included by default

**Fix** 
- Added `javafx-media` dependency to `pom.xml`
- Ensured JavaFX version matched existing UI dependencies 
- Reloaded Maven project
- Added `requires javafx.media;` to `module-info.java`

**Outcome**
- Media classes resolved correctly
- Application progressed to runtime media loading stage

## Resource Loading Debugging

**Issue** 
- Application crashed with `NullPointerException` when loading audio resource.

**Investigation**
- Used debug output to confirm `getResource(...)` returned `null`
- Identified mismatch between audio filename and resource path
- Confirmed correct resource packaging under `target/classes`

**Resolution**
- Corrected resource path to match actual filename
- Verified resource URL printed correctly at runtime

**Outcome**
- Audio file successfully located on classpath
- Media initialisation proceeded to next stage

## Media Compatibility Issues (Linux)

**Issue**
- JavaFX `MediaPlayer` failed at runtime with:
  “Could not create player!”
  and `ERROR_MEDIA_AUDIO_FORMAT_UNSUPPORTED`

**Investigation**
- Error occurred even with valid resource paths
- Confirmed issue was not related to Maven or Java code
- Identified Linux JavaFX media backend dependency on native codecs

**Attempted Fix**
- Converted MP3 audio file to WAV
- Retested using both `MediaPlayer` and `AudioClip`

**Outcome**
- Error persisted, indicating missing native media backend rather than format issue


## JavaFX Media Backend Resolution (Linux)

**Root Cause**
- JavaFX media on Linux depends on native GStreamer plugins
- Required plugins were not installed on the system

**Resolution**
- Installed GStreamer and codec plugins:
  - gstreamer1.0-plugins-base
  - gstreamer1.0-plugins-good
  - gstreamer1.0-plugins-bad
  - gstreamer1.0-plugins-ugly
  - gstreamer1.0-libav
- Restarted IDE to reload native libraries

**Outcome**
- JavaFX successfully created media players
- Background audio played continuously during application runtime
- Audio stopped cleanly when application exited via JavaFX lifecycle `stop()`

**Status**
- Feature complete and stable

## Final Status - Audio

- Continuous background audio implemented 
- Audio initialised once at application startup 
- Audio loops throughout application runtime 
- Audio stops cleanly on application exit 
- Cross-platform media limitations investigated and resolved 

--- 

## Stock Shortage Handling

**Goal** 
- Prevent customers from placing orders that exceed available stock 
- Ensure stock validation occurs at checkout using up-to-date database values. 

**Implementation Details**
- Updated `CustomerModel.checkOut()` to validate stock before order creation.
- Grouped trolley items by Product ID to optimise stock checking.
- Used `databaseRW.purchaseStocks(...)` to atomically verify and update stock levels.
- If insufficient stock is detected:
  - Checkout process is blocked.
  - A detailed message is generated listing affected products, requested quantity, and available stock.
  - Products with insufficient stock are removed from the trolley.
  - Customer is notified using `RemoveProductNotifier` popup.
- If stock is sufficient:
  - Order is created via `OrderHub`.
  - Trolley is cleared.
  - Receipt is generated and displayed.

**Problems Encountered**
- Ensuring popup notifications were not immediately closed after display.
- Preventing stale receipt data from remaining visible after failed checkout attempts.

**Resolution**
- Adjusted popup lifecycle so it remains visible until user action.
- Cleared receipt output when checkout fails.
- Updated trolley display dynamically after product removals.

**Outcome**
- Checkout now reliably prevents invalid orders due to stock shortages.
- Customer receives clear feedback and can retry checkout with updated trolley contents.
- Feature tested successfully with both sufficient and insufficient stock scenarios.

---

## Current Status 

- Organized Trolley: Complete 
- Flexible Search: Complete 
- Stock Shortage Handling: Complete
- Background Sound: Complete 