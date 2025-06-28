//
//  IconResources.swift
//  iosApp
//
//  Created by Andrew Stephens on 6/24/25.
//

import Foundation
import SwiftUI
import sharedKit

extension IconResources.IconResource {
    
    func toView() -> some View {
        
        switch self {
            case .news: NewsIcon()
            default : fatalError("Unhandled Icon")
        }
        
    }
    
}
