package net.cofcool.data.mybatis;

import net.cofcool.data.mybatis.metadata.MetadataManager;

public abstract class MetadataHelper {

    private static MetadataManager metadataManager;

    MetadataHelper() {
    }

    public static MetadataManager getMetadataManager() {
        return metadataManager;
    }

    public static void setMetadataManager(MetadataManager metadataManager) {
        if (MetadataHelper.metadataManager != null) {
            throw new IllegalStateException("MetadataHelper already held metadataManager");
        }
        MetadataHelper.metadataManager = metadataManager;
    }

}
