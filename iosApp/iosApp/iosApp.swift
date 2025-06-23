//
//  iosAppApp.swift
//  iosApp
//
//  Created by Andrew Stephens on 5/16/25.
//

import SwiftUI
import sharedKit

@main
struct iosApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
        
        let evidence = EvidenceResources.EvidenceAnimation.dots
    }
}
