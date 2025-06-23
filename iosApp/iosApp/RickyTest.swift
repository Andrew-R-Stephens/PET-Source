
import SwiftUI
import CoreData

#Preview {
    RickyTest()
}

struct RickyTest: View {
    var body: some View {
        
        ZStack(
            alignment: .top
        ) {
            VStack {
                HStack {
                    Button("Hamburger") {
                        print("Hamburger tapped!")
                    }
                    
                    Button("Newsfeed") {
                        print("Newsfeed tapped!")
                    }
                    
                    Button("Review") {
                        print("Review tapped!")
                    }
                    
                    Button("Account") {
                        print("Account tapped!")
                    }
                }
                Image("")
                    .resizable()
                    .scaledToFit()
                    .frame(height: 200)
            }
        }
        .background(Color.black)
    }
}
