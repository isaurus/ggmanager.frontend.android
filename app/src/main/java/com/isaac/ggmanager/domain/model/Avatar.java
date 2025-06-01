package com.isaac.ggmanager.domain.model;

import com.isaac.ggmanager.R;

public enum Avatar {
    AVOCADO("ic_avatar_avocado", R.drawable.ic_avatar_avocado),
    BATMAN("ic_avatar_batman", R.drawable.ic_avatar_batman),
    CACTUS("ic_avatar_cactus", R.drawable.ic_avatar_cactus),
    SHEEP("ic_avatar_sheep", R.drawable.ic_avatar_sheep),
    SLOTH("ic_avatar_sloth", R.drawable.ic_avatar_sloth),
    ZOMBIE("ic_avatar_zombie", R.drawable.ic_avatar_zombie);

    private final String key;
    private final int drawableResId;

    Avatar(String key, int drawableResId) {
        this.key = key;
        this.drawableResId = drawableResId;
    }

    public String getKey() {
        return key;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public static Avatar fromKey(String key) {
        for (Avatar avatar : values()) {
            if (avatar.key.equals(key)) {
                return avatar;
            }
        }
        return AVOCADO;
    }
}
