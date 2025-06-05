package com.isaac.ggmanager.domain.model;

import com.isaac.ggmanager.R;

/**
 * Enumeración que representa los diferentes avatares disponibles en la aplicación.
 * Cada avatar está asociado a una clave única (key) y un recurso drawable para su representación gráfica.
 *
 * <p>Esta enumeración facilita la gestión y el acceso a los avatares mediante su clave o recurso gráfico.</p>
 */
public enum Avatar {

    AVOCADO("ic_avatar_avocado", R.drawable.ic_avatar_avocado),
    BATMAN("ic_avatar_batman", R.drawable.ic_avatar_batman),
    CACTUS("ic_avatar_cactus", R.drawable.ic_avatar_cactus),
    SHEEP("ic_avatar_sheep", R.drawable.ic_avatar_sheep),
    SLOTH("ic_avatar_sloth", R.drawable.ic_avatar_sloth),
    ZOMBIE("ic_avatar_zombie", R.drawable.ic_avatar_zombie),
    CHAPLIN("ic_avatar_zombie", R.drawable.ic_avatar_chaplin),
    CHILD("ic_avatar_child_girl", R.drawable.ic_avatar_child_girl),
    COFFEE("ic_avatar_coffee", R.drawable.ic_avatar_coffee),
    HEISENBERG("ic_avatar_heisenberg", R.drawable.ic_avatar_heisenberg),
    HIPSTER("ic_avatar_hipster", R.drawable.ic_avatar_hipster),
    JASON("ic_avatar_jason", R.drawable.ic_avatar_jason);

    /**
     * Clave única que identifica al avatar.
     */
    private final String key;

    /**
     * Identificador del recurso drawable que representa gráficamente el avatar.
     */
    private final int drawableResId;

    /**
     * Constructor privado que asigna la clave y el recurso drawable a cada avatar.
     *
     * @param key Clave única del avatar.
     * @param drawableResId ID del recurso drawable asociado.
     */
    Avatar(String key, int drawableResId) {
        this.key = key;
        this.drawableResId = drawableResId;
    }

    /**
     * Obtiene la clave única que identifica al avatar.
     *
     * @return Clave del avatar.
     */
    public String getKey() {
        return key;
    }

    /**
     * Obtiene el identificador del recurso drawable asociado al avatar.
     *
     * @return ID del recurso drawable.
     */
    public int getDrawableResId() {
        return drawableResId;
    }

    /**
     * Devuelve el avatar correspondiente a la clave proporcionada.
     * Si no existe ningún avatar con la clave dada, se devuelve el avatar por defecto {@link #AVOCADO}.
     *
     * @param key Clave única del avatar a buscar.
     * @return Avatar asociado a la clave, o {@link #AVOCADO} si no se encuentra ninguno.
     */
    public static Avatar fromKey(String key) {
        for (Avatar avatar : values()) {
            if (avatar.key.equals(key)) {
                return avatar;
            }
        }
        return AVOCADO;
    }
}
