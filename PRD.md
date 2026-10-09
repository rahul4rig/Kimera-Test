# Product Requirements Document (PRD): Kimera AI Style Preview

**Document Version:** 1.0  
**Product Name:** Kimera AI Style Preview  
**Target Pilot Location:** Kimera Salon — Jayanagar 4th Block, Bengaluru  
**Platform:** Android (Kotlin, Jetpack Compose, Room Local Persistence)  
**Design System:** Warm Boutique Salon  

---

## 1. Executive Summary & Vision
**Kimera AI Style Preview** is a chair-side consultation and hairstyle visualization application built for luxury boutique barbershops and salons. During a live consultation, clients often struggle to articulate how a reference haircut will look on their unique face structure, hair texture, and cranial contours—leading to hesitation, miscommunication, or post-cut dissatisfaction.

Kimera solves this by providing a structured, **4-step chair-side workflow** that captures client consent, takes a quick chair photo, generates 3 bespoke style variations tailored to the client's facial contours in under 60 seconds, aligns the stylist and client on micro-adjustments before the first scissor cut, and logs the final haircut outcome to measure pilot KPIs.

---

## 2. Pilot Objectives & Success Metrics (21-Day Pilot)
The application is engineered to track and validate a 21-day salon pilot across two cohorts (testing a **₹100 flat preview fee** vs. a **2% service commission model**).

| KPI / Metric | Target Threshold | Definition |
| :--- | :--- | :--- |
| **Preview Accuracy (Match Rate)** | **66%+** | Percentage of completed cuts rated as "Matched Perfectly" against the chosen AI preview. |
| **Barber Tool Adoption** | **70%+** | Percentage of consultations where the stylist actively used the blueprint and directives during the cut. |
| **Client Trust (Consent Rate)** | **80%+** | Percentage of clients who grant camera & AI preview consent at the start of the session. |
| **Render Speed SLA** | **< 60s (Target) / < 120s (Max)** | 8 out of 10 sessions must complete preview generation within the target time window. |
| **Visual Realism Rating** | **4.0+ / 5 Stars** | Post-cut realism and fidelity score logged during the Outcome Check. |

---

## 3. Target Users & Personas
1. **Lead Stylist / Barber (Vikram, Anand, Sameer)**
   - **Needs:** Fast, zero-friction chair-side operation; clear technical guardrails (fade height, clipper guard, crown texture); ability to log specific pre-cut adjustments (e.g., *"Fade 1cm lower on right temple"*).
2. **Salon Client (e.g., Rohan V.)**
   - **Needs:** Explicit privacy reassurance regarding their photo; visual confidence before committing to a new style or shorter fade; collaborative alignment with their stylist.
3. **Salon Pilot Manager / Owner**
   - **Needs:** Real-time visibility into session volume, pricing model performance (`₹100 Fee` vs. `2% Cut`), consent opt-in rates, and outcome match accuracy.

---

## 4. End-to-End User Journey & Functional Requirements

### Screen 0: New Consultation (`SessionStartScreen`)
- **Top Bar:** Displays the lowercase geometric `kimera` wordmark on the left and an interactive `Chair 03 ●` status pill on the right (tapping opens the Pilot Analytics & Sessions sheet).
- **Hero Visual:** Showcases the Kimera salon suite interior with a `Suite North · Ready` status overlay.
- **Session Configuration:**
  - **Client Name Input:** Pre-filled or editable client name field (`Rohan V.`).
  - **Stylist Selector:** Single-select pill chips for active stylists (`Vikram`, `Anand`, `Sameer`) with a coral active dot indicator.
- **Process Overview Card:** Displays the `CONSULTATION PROCESS` (`4 Steps` badge) with a 4-segment progress preview (`01 Consent`, `02 Capture`, `03 Previews`, `04 Cut & Check`).
- **Primary Action:** Full-width coral pill button (`Start Session →`).

### Step 1 of 4: Photo & Privacy (`ConsentScreen`)
- **Progress Track:** 4-segment bar with `1. CONSENT` highlighted in deep coral.
- **Privacy Controls:**
  - **Camera & AI Preview (`Required` badge):** Explains that the photo is used strictly during the live consultation to adapt hairstyles. Uses a high-contrast **Green Toggle Switch (`#34C759`)** when active.
  - **Save for Next Visit (`Optional` badge):** Allows the client to opt into saving their adapted blueprint on file for future visits. Also uses the **Green Toggle Switch (`#34C759`)**.
- **Primary Action:** `Continue to Photo →` button (enabled only when Required Camera Consent is toggled on).
- **Fallback / Opt-Out Flow (`DeclinedConsentScreen`):**
  - Clicking `💬 Skip AI Preview (Verbal Consultation)` routes the session to a dedicated **Standard Consultation Active** screen.
  - Logs the session as a voluntary client opt-out without penalizing tool accuracy metrics, and allows the stylist to either proceed with a standard verbal haircut or re-enable AI preview if the client changes their mind.

### Step 2 of 4: Take Photo & Pick Style (`ReferenceCaptureScreen`)
- **Header & Progress:** Displays `Capture` and a `Step 2 of 4` pill badge, with Steps 1 & 2 active on the progress bar.
- **Client Photo Card:**
  - Displays the client's name (`Rohan V.`) and a large portrait preview.
  - Includes a floating `📷 Retake` pill button integrated with the zero-permission Android Photo Picker (`ActivityResultContracts.PickVisualMedia`) so stylists can select/retake a fresh chair photo.
- **Target Style Card:**
  - Displays the target reference cut (`Textured French Crop` — *Modern taper fade · Low maintenance*) with a thumbnail, verified checkmark icon, and a `Change` toggle to cycle target styles.
- **Primary Action:** Dark terracotta pill button (`GENERATE 3 PREVIEWS`) with an SLA indicator (`🕒 Takes under 60 seconds`).

### Step 2.5: Crafting Your Previews (`GeneratingPreviewScreen`)
- **Editorial Staging:** Centered `kimera` wordmark, `Crafting Your Previews` serif headline (with generous `42.sp` line height), and subtitle (*"Infusing natural lighting and fade depth tailored to your contours."*).
- **Atmospheric Visualizer:**
  - Animated pulsing radial peach/coral glow orb (`SoftPeach` & `KimeraCoral`).
  - Central circular card with a sparkle icon badge, `Generating 3 Previews` title, and `BESPOKE TEXTURES` sub-label.
- **Live Telemetry:**
  - Status pill: `● Adapting fade & texture to face structure...`
  - Live elapsed stopwatch pill: `00:XX / Under 60s`
  - Smooth horizontal progress bar tracking generation progress.
- **Location Footer:** `kimera` wordmark with `Bengaluru · Jayanagar 4th Block`.

### Step 3 of 4: Pick Your Style (`RenderResultsScreen`)
- **Progress & Counter:** Highlights Step 3 (`Previews`) and displays `STEP 3 OF 4 · STYLE PREVIEWS` with a `1 of 3` variation counter.
- **Interactive Variation Carousel Card:**
  - Displays high-resolution adapted hairstyle renders:
    1. **Variation A (`Textured French Crop`):** *Top Stylist Match* — Clean high-taper fade with natural cropped texture (`Low Maintenance`, `Clean Fade`, `Daily Friendly`).
    2. **Variation B (`Mid Drop Taper Crop`):** *High Definition* — Graduated drop fade curving behind the ears (`Crisp Directional Flow`, `Mid Drop (#1 to #2)`, `Styling Powder`).
    3. **Variation C (`Scissor Soft Flow Crop`):** *Organic Flow* — Zero-fade natural scissor taper for relaxed daily wear (`Natural Texture`, `Scissor Taper`, `Light Matte Clay`).
  - Interactive pagination dots (`● ○ ○`) and a `⟳ Regenerate with custom notes` action to cycle variations.
- **Primary Action:** `✓ Confirm Selected Look` coral pill button.

### Step 4A of 4: Stylist Alignment / Discussion (`SelectionDiscussionScreen`)
- **Header & Progress:** Displays `STEP 4 OF 4 · STYLIST ALIGNMENT` and `Discussion` with all 4 progress bars active.
- **Confirmed Blueprint Anchor:** Hero card showing the chosen render, style title, `Front Profile Reference` subtitle, and a `✓ Confirmed` badge.
- **Stylist Adjustments Card:**
  - Live counter (`3 applied`) tracking toggleable technical chips:
    - `✔ Slightly longer fringe`
    - `✔ Low skin fade (0.5)`
    - `✔ Natural matte texture`
  - **Stylist Note Input:** Editable text field (pre-populated with `"Fade 1cm lower on right temple"`) for bespoke clipper/scissor instructions.
- **Primary Action:** Deep terracotta pill button (`✂ Ready to Cut`).

### Step 4B of 4: Outcome Log (`OutcomeLogScreen`)
- **Header & Progress:** Displays `Outcome Log` and `STEP 4 OF 4 · FINAL REVIEW`.
- **Dual Comparison Card:**
  - Side-by-side visual verification comparing the **Chosen Preview** (left) with the **Completed Cut / Chair Station 2** placeholder (right), bridged by a central swap icon badge (`⇄`).
- **Match Classification Selector:**
  1. `✓ Matched Perfectly` ( 😊 ) — Active coral tint pill card.
  2. `— Close Match` ( 😐 ) — Warm cream pill card.
  3. `✕ Did Not Match` ( ☹️ ) — Warm cream pill card.
- **Stylist & Client Notes:** Multi-line input box (`"Client loved the texture adaptation and lower fade."`).
- **Primary Action:** `Complete Consultation ✓` coral pill button.

### Post-Session Summary & Celebration (`SessionCompleteScreen`)
- **Consultation Finalized Banner:** Green check badge confirming session completion with client and stylist metadata.
- **Styling Blueprint Summary Card:** Displays the final chosen style thumbnail, target reference, match status pill (`MATCHED`), 5-star realism rating, and recorded stylist directive.
- **Pilot Success Metrics Card:** Live calculation of the 4 core 21-day pilot KPIs (`Preview Accuracy`, `Barber Tool Adoption`, `Client Trust`, `Render Speed`).
- **Celebratory Confetti & Next Session Action:**
  - Clicking **`Start Next Chair Session ↻`** triggers a full-screen, 110-particle physics-based **Confetti Explosion (`ConfettiOverlay`)** in Kimera's boutique palette before transitioning to a fresh chair session after a 1.5-second celebratory cascade.
- **Pilot History Access:** Secondary button (`View Pilot History & Analytics`) opens the `PilotDashboardSheet` modal to filter sessions by pricing model (`All`, `₹100 Fee Model`, `2% Service Model`) and inspect historical logs.

---

## 5. Visual Design & Typography System ("Warm Boutique Salon")

### Color Palette (`Color.kt` & `Theme.kt`)
| Token Name | Hex Code | Usage |
| :--- | :--- | :--- |
| `WarmCream` | `#FFF8F2` | Primary application canvas background |
| `Ivory` | `#F6EFE8` | Elevated card containers and secondary pills |
| `RenderWhite` | `#FFFFFF` | High-emphasis cards, input fields, and switch thumbs |
| `Charcoal` | `#272321` | Primary espresso-black typography for maximum legibility |
| `WarmGrey` | `#706865` | Secondary subtitles, metadata, and inactive navigation icons |
| `KimeraCoral` | `#F27D6F` | Signature primary pill buttons and active progress bars |
| `DeepCoral` | `#A03F35` | High-contrast terracotta CTA buttons (`GENERATE 3 PREVIEWS`, `Ready to Cut`) & badges |
| `SoftPeach` / `PeachTint` | `#F9C5B9` / `#FDE8E3` | Ambient radial glow orb, active chip fills, and step badges |
| `BorderHairline` | `#E8D8CA` | 1dp structural card borders and dividers |
| `ToggleGreen` | `#34C759` | Active track fill for privacy & consent toggle switches |

### Typography & Spacing Rules
- **Brand Wordmark (`KimeraWordmark`):** Lowercase geometric sans-serif (`kimera`), `ExtraBold`, `1.sp` letter spacing, with explicit `lineHeight = (fontSize + 6).sp` to prevent ascender clipping.
- **Editorial Headlines:** Serif display family (`FontFamily.Serif`, `Bold`, `30.sp–32.sp`) with explicit `lineHeight = 38.sp–42.sp` so multi-line titles (such as *"Crafting Your Previews"*) never collide vertically.
- **Section Trackers:** Uppercase sans-serif (`10.sp–11.sp`, `ExtraBold`, `1.sp` letter spacing) in `DeepCoral`.

---

## 6. Technical Architecture & Data Model
- **UI Layer:** 100% Jetpack Compose with Material 3 components, custom Canvas animations (`GeneratingPreviewScreen` radial orb and `ConfettiOverlay` particle engine), and persistent 4-tab bottom navigation (`Session`, `Preview`, `Styling`, `Journal`).
- **State Management:** `KimeraSessionViewModel` managing reactive `StateFlow` streams for the active `ChairSession`, `RenderProgressState`, `BarberAdvice`, and `PilotMetrics`.
- **Local Persistence (Room Database):**
  - `ChairSession` entity stored in `AppDatabase` via `SessionDao`.
  - Pre-seeded with realistic historical Jayanagar pilot sessions so analytics and cohort filters work out of the box.
- **AI Stylist Advisory Service (`GeminiStylistService`):** Generates contextual barber guardrails and facial adaptation tips based on face shape, hair texture, and fade selection.
