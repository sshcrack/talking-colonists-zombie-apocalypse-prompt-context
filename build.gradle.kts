import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

modSettings {
    clientOptions {
        fov = 90
        guiScale = 3
        narrator = false
        darkBackground = true
        musicVolume = 0.0
    }
}


// Example of overriding publishing settings
publishMods {
    curseforge {
        clientRequired = true // Set as needed
        serverRequired = true // Set as needed
    }
}

repositories {
    maven {
        name = "sshcrack"
        url = uri("https://maven.sshcrack.me/releases")
    }
}

dependencies {
    modImplementation("me.sshcrack:mc_talking:${project.property("mc_talking_version")}-${project.name}")
}
