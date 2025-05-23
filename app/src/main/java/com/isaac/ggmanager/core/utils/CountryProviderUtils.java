package com.isaac.ggmanager.core.utils;

import android.content.Context;

import com.isaac.ggmanager.R;

import java.util.Arrays;
import java.util.List;

public class CountryProviderUtils {
    public static List<String> getCountries(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.countries_array));
    }
}
