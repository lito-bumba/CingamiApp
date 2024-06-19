import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        KoinInit()
            .doInit(
                appDeclaration: { _ in  }
            )
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
