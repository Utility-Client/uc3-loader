package org.utilityclient.loader.discovery;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.discovery.ModCandidateFinder;

import java.nio.file.Path;

public class ClientModCandidateFinder implements ModCandidateFinder {
    @Override
    public void findCandidates(ModCandidateConsumer out) {
        Path gameDir = FabricLoader.getInstance().getGameDir();
        out.accept(gameDir.resolve("libraries/utilityclient/uc.jar"), true);
    }
}
