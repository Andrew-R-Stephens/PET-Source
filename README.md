
<img width="100%" height="100%" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/pet-repo-profile_thin.png" alt=""/>

<div align='center'><h1>Phasmophobia Evidence Tool</h1></div>

> Phasmophobia Evidence Tool (PET) is a companion app for the game Phasmophobia. PET enhances the user experience by supplementing in-game mechanics with a more involved investigation system.

<div align='center'>
    <a href='https://play.google.com/store/apps/details?id=com.tritiumstudios.phasmophobiaevidencepicker&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img
        width="150" alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>
</div>
<div align='center'><code>[ iOS Coming Soon ]</code></div>

<div align='left'> 
    <h2>Author</h2>
    <div><a href="https://github.com/Andrew-R-Stephens">@Andrew-R-Stephens</a></div>
</div>

<br>

<div align='left'> 
    <h2>Index</h2>
    <div><a href="#demo-screenshots">Demo Screenshots</a></div>
    <div><a href="#design-and-architecture">Design and Architecture</a></div>
    <div><a href="#technologies-used">Technologies Used</a></div>
    <div><a href="#app-features">Features</a></div>
    <div><a href="#acknowledgements">Acknowledgements</a></div>
</div>

<br>

<div align='left'><h2>Demo Screenshots</h2></div>
<div align='center'>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_1.png"><img title="Title Screen" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_1_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_4.png"><img title="Evidence Fragment (Not Interacted)" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_4_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_7.png"><img title="Evidence Fragment (Interacted)" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_7_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_8.png"><img title="Missions Fragment" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_8_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_5.png"><img title="Ghost Type Information" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_5_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_6.png"><img title="Evidence Type Information" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_6_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_9.png"><img title="Map Select Fragment" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_9_thumbnail.png" alt="screenshot" /></a>
    <a href="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_10.png"><img title="Interactive Map Fragment" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/screenshot_10_thumbnail.png" alt="screenshot" /></a>
</div>

<br>

<br>

<div align='center'><h2>Design and Architecture</h2></div>

<br>
<img title="Project Modules" src="https://github.com/Andrew-R-Stephens/Andrew-R-Stephens/blob/master/assets/pet_modularization_2.png" alt="graph" />
<br>

<div align='left'><h3>Modules</h3></div> 
[ <code>active</code> ]
<ul>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/app-compose"><code>:app:compose</code></a> : The Android core module.<br>
        <ul>
            <li>Implements the <em>shared</em> KMP module.</li>
            <li>It is a completely reworked <em>app-legacy</em>.</li>
            <li>Adopts modern Jetpack Compose / Clean Code approach.</li>
        </ul>
    </li>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/core-resources"><code>:core:resources</code></a> : App core resources.<br>
        <ul>
            <li>Contains core resources that can be shared between features and core app.</li>
            <li>Currently ongoing integration with <em>app-compose</em> and <em>app-legacy</em> modules.</li>
        </ul>
    </li>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/core-ui"><code>:core:ui</code></a> : App core UI.<br>
        <ul>
            <li>Implements <em>:core:resources</em> module.</li>
            <li>Currently ongoing integration with <em>app-compose</em> module.</li>
        </ul>
    </li>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/shared"><code>:shared</code></a> : The KMP project module.
        <ul>
            <li>Shared KMP domain layer.</li>
            <li>Currently ongoing integration with <em>app-compose</em> module.</li>
        </ul>
    </li>
</ul>
[ <code>incubating</code> ]
<ul>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/iosApp"><code>:iosApp</code></a> : The iOS project files.
        <ul>
            <li>Dependent on the <em>shared</em> module.</li>
        </ul>
    </li>
</ul>
[ <code>sunset (production)</code> ]
<ul>
    <li><a href="https://github.com/Andrew-R-Stephens/PET-Source/tree/master/app-legacy"><code>:app:legacy</code></a> : Current Live module.
        <ul>
            <li>Maintained for live hotfixes.</li>
        </ul>
    </li>
</ul>

<br>

<div align='left'><h3>Technologies Used</h3></div>
<div align='center' style="display:float">
  <img src="https://img.shields.io/badge/-Kotlin-7F52FF?logo=kotlin&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Swift-F05138?logo=swift&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-SwiftUI-F05138?logo=swift&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-KMP-7F52FF?logo=kotlin&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Android%20Studio-3DDC84?logo=androidstudio&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Xcode-147EFB?logo=xcode&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Java-F80000?logo=oracle&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Gradle-02303A?logo=gradle&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-JSON-black?logo=json&logoColor=white&style=bold" alt=""/>
</div> 
<div align='center' style="display:float">
  <img src="https://img.shields.io/badge/-Google%20Play-E37400?logo=googleplay&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Google%20Analytics-E37400?logo=googleanalytics&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Google%20Firebase-FFCA28?logo=firebase&logoColor=white&style=bold" alt=""/>
  <img src="https://img.shields.io/badge/-Google%20Admob-EA4335?logo=googleadmob&logoColor=white&style=bold" alt=""/>
</div>

<div align='left' style="display:float">
  <h3>High-Level Design</h3>
  <div align='center'><label><b>Native Android -> Kotlin Multiplatform (Android / iOS) Integration Pattern</b></label></div>
  &emsp;<img src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/pet_migration_flow_stage4.png" alt=""/>
  <blockquote>P.E.T. has become Kotlin-first! Major code migration started in May 2024 and finished in July 2024. Planned migration to KMP will commence after confirmation of build stability and feature refinement.</blockquote>
  <ul>
    <li>
        <h5>Build for Android ♻️</h5>
        <ul>
            <li>Refactor from Java to Kotlin ✔️</li>
            <li>Refactor to Clean Code ✔️</li>
            <li>Migrate from Views to Jetpack Compose ♻️</li>
        </ul>
    </li>
    <li>
        <h5>Prepare KMP ✔️</h5>
        <ul>
            <li>Add KMP and iOS project module ✔️</li>
            <li>Build shared domain layer✔️</li>
        </ul>
    </li>
    <li>
        <h5>Build for iOS ♻️</h5>
        <ul>
            <li>Begin migration to iOS/SwiftUI ♻️</li>
            <li>(Interim steps) ♻️</li>
            <li>Test for iOS 🔳</li>
            <li>Publish on iOS App Store 🔳</li>
        </ul>
    </li>
  </ul>
</div>

<!--
<div align='left' style="display:float">
  <h3>Architecture</h3>
</div>
-->

<br>

<div align='left' style="display:float">
  <h3>Internal Software Solutions</h3>
    <a href="https://github.com/Andrew-R-Stephens/PET-MapMaker">P.E.T. Map Maker</a>
    <blockquote>Assists with the development of interactive maps.</blockquote>
    <div><a href="https://github.com/Andrew-R-Stephens/PET-MapMaker"><img width=480 title="PET Map Maker Demo" src="https://raw.githubusercontent.com/Andrew-R-Stephens/Andrew-R-Stephens/master/assets/PET_Map_Maker_Demo.png" alt="screenshot" /></a></div>
</div>

<br>

<div>
    <h3>App Features</h3>
    <ul>
        <li>Investigation tool scores and displays ghosts based on the evidence provided and the difficulty chosen.</li>
        <li>Hunt timer and hunt warning audio indicator.</li>
        <li>Interactive Map which displays the location of rooms, cursed possessions, and fuse box.</li>
        <li>Sanity meter keeps track of Sanity based on selected difficulty and map size.</li>
        <li>Ghost Information</li>
        <ul>
            <li>Strengths, Weakness, General Data</li>
            <li>Hunt Information</li>
            <li>Notable attributes</li>
        </ul>
        <li>Evidence Information</li>
        <ul>
            <li>General Data about the Evidence</li>
            <li>Tools required and what each tool tier does</li>
        </ul>
        <li>Equipment Information</li>
        <ul>
            <li>Unlock Level, Unlock Cost, and Purchase Cost.</li>
            <li>Shop flavor text and useful information from the shop.</li>
            <li>Positive and negative attributes of each tier.</li>
        </ul>
        <li>Thematic resemblance to Phasmophobia.</li>
        <li>Kept up-to-date with Phasmophobia changes.</li>
    </ul>
    <h3>Accessibility Features</h3>
    <ul>
        <ul>
            <li>Native support for multiple Languages: English, Chinese (Simplified), Czech, French, German, Italian, Japanese, Portuguese, Russian, Spanish</li>
            <li>Colorblind Themes</li>
            <li>Device support: Phones, Tablets, and Desktop</li>
            <li>Orientation support: Portrait / Landscape</li>
        </ul>
        <li>RSS Feed pulled from official Phasmophobia changelogs on Steam.</li>
        <li>Access to the PET support Discord.</li>
    </ul>
</div>

<br>

<div align='left'>
    <h2>Acknowledgements</h2>
</div>
<div align='center'>
    <div><a href="https://store.steampowered.com/app/739630/Phasmophobia/"><h3>Phasmophobia (Official Game)</h3></a></div> 
    <div><a href="https://store.steampowered.com/app/739630/Phasmophobia/"><img width="30%" src="https://cdn.akamai.steamstatic.com/steam/apps/739630/header.jpg?t=1693954450"/></a></div>
    <h4><label>Thank you to the team at Kinetic Games for such an amazing game!</label></h4>
    <h4><label>And a huge thanks to Daniel Knighter for his permission to use the name and likeness Phasmophobia for this project!</label></h4>
</div>

<br>

<div align='left'>
    <h2>Roadmap</h2>
    &emsp;<a href="https://trello.com/b/E124v6Pt/phasmophobia-evidence-tool-pet">Trello</a>
</div>

<br>

<div align='left'>
    <h2>License</h2>
    &emsp;<a href="https://github.com/TritiumGaming/Phasmophobia-Evidence-Picker-Privacy-Policy/blob/main/Privacy%20Policy">License</a>
</div>
