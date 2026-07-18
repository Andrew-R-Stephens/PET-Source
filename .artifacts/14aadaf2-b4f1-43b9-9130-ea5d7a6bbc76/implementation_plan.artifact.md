# Implementation Plan - Proximal Walkthrough Card Positioning (Refined)

The goal is to position the walkthrough info card as close as possible to the focused component without overlaying it. This reverts the "edge-aligned" logic in favor of a proximal one that intelligently uses available space.

## Proposed Changes

### [core-ui]

#### [MODIFY] [WalkthroughHost.kt](file:///Users/andrew/AndroidStudioProjects/PET-Source/core-ui/src/main/kotlin/com/tritiumgaming/core/ui/widgets/walkthrough/WalkthroughHost.kt)
- Update the `Layout` measurement and placement logic:
    - Instead of placing the card at the screen edges, calculate its position relative to the `aggregateRect` (the union of all focused component bounds).
    - For the chosen "best" side (Top, Bottom, Left, or Right):
        - **TOP**: Position just above `aggregateRect.top` (minus a small gap).
        - **BOTTOM**: Position just below `aggregateRect.bottom` (plus a small gap).
        - **LEFT**: Position just to the left of `aggregateRect.left` (minus a small gap).
        - **RIGHT**: Position just to the right of `aggregateRect.right` (plus a small gap).
    - Maintain axis-centering relative to the `aggregateRect`.
    - Apply screen-boundary clamping to ensure the card stays fully visible, but prioritize the proximal placement.

## Verification Plan

### Automated Tests
- Build the project to ensure no compilation errors.

### Manual Verification
- Trigger walkthroughs for various components:
    - Verify the info card "hugs" the component closely.
    - Verify it moves to the side with the most space.
    - Verify it stays within screen bounds.
