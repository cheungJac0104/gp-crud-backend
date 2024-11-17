package com.example.gp.gp_crud_backend.utilities;
import java.util.HashMap;
import java.util.Map;

public enum UserActivity {
    LOGIN("User logged in"),
    DONATION("User made a donation"),
    UPDATE_PROFILE("User updated profile");

    private final String description;

    UserActivity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Static map to hold the associations
    private static final Map<UserActivity, String> activityMap = new HashMap<>();

    // Static block to initialize the map
    static {
        for (UserActivity activity : UserActivity.values()) {
            activityMap.put(activity, activity.getDescription());
        }
    }

    // Method to get the description from the map
    public static String getDescription(UserActivity activity) {
        return activityMap.get(activity);
    }
}
