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
- Database access is handled via 'DatabaseRW' and 'DerbyRW'

**Outcome** 
- Ready to begin implementing first feature 

--- 

## Organized Trolley Feature

**Tasks** 
- Implement organized trolley display 
- Merge duplicate products into a single line with quantity 
- Sort trolley items by Product ID (ascending)

**Implementation Details** 
- Changes made in 'ProductListFormatter.buildString()'
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
- Updated 'CustomerModel.search()'
- ID search uses 'searchByProductID'
- Name search uses 'databaseRW.searchProduct()' (same method as Warehouse)
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


**Tasks** 


--- 

## Current Status 

- Organized Trolley: Complete 
- Flexible Search: Complete 
- Stock Shortage Handling: Planned
- Background Sound: Planned 