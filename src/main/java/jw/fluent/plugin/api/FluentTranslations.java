package jw.fluent.plugin.api;

public class FluentTranslations
{

    public static class GUI
    {

        public static class BASE
        {

            public static class INSERT
            {

                // Insert
                public static final String TITLE = "gui.base.insert.title";
            }

            public static class DELETE
            {

                // Delete
                public static final String TITLE = "gui.base.delete.title";
            }

            public static class EDIT
            {

                // Edit
                public static final String TITLE = "gui.base.edit.title";
            }

            public static class SEARCH
            {

                // Search
                public static final String TITLE = "gui.base.search.title";

                public static class DESC
                {

                    // Search
                    public static final String LEFT_CLICK = "gui.base.search.desc.left-click";

                    // Change option
                    public static final String RIGHT_CLICK = "gui.base.search.desc.right-click";

                    // Reset
                    public static final String SHIFT_CLICK = "gui.base.search.desc.shift-click";
                }
            }

            public static class PAGE_UP
            {

                // Page up
                public static final String TITLE = "gui.base.page-up.title";
            }

            public static class PAGE_DOWN
            {

                // Page down
                public static final String TITLE = "gui.base.page-down.title";
            }

            public static class EXIT
            {

                // Exit
                public static final String TITLE = "gui.base.exit.title";
            }

            public static class BACK
            {

                // Go back
                public static final String TITLE = "gui.base.back.title";
            }

            // value
            public static final String VALUE = "gui.base.value";

            // active
            public static final String ACTIVE = "gui.base.active";

            // inactive
            public static final String INACTIVE = "gui.base.inactive";

            // State
            public static final String STATE = "gui.base.state";

            // Mouse click info
            public static final String CLICK_INFO = "gui.base.click-info";

            // Left
            public static final String LEFT_CLICK = "gui.base.left-click";

            // Right
            public static final String RIGHT_CLICK = "gui.base.right-click";

            // Shift
            public static final String SHIFT_CLICK = "gui.base.shift-click";

            // Save
            public static final String SAVE = "gui.base.save";

            // Cancel
            public static final String CANCEL = "gui.base.cancel";

            // Title
            public static final String TITLE = "gui.base.title";

            // Icon
            public static final String ICON = "gui.base.icon";

            // Next
            public static final String NEXT = "gui.base.next";

            // Remove
            public static final String REMOVE = "gui.base.remove";

            // Previous
            public static final String PREVIOUS = "gui.base.previous";

            // Increase
            public static final String INCREASE = "gui.base.increase";

            // Decrease
            public static final String DECREASE = "gui.base.decrease";

            // Select
            public static final String SELECT = "gui.base.select";

            // Enable / Disable
            public static final String ENABLE_DISABLE = "gui.base.enable-disable";

            // Example
            public static final String EXAMPLE = "gui.base.example";
        }
    }

    public static class PERMISSIONS
    {

        // You need to have one of those permissions
        public static final String ONE_REQUIRED = "permissions.one-required";

        // You need to have permissions
        public static final String ALL_REQUIRED = "permissions.all-required";

        // Incorrect number of arguments, should be
        public static final String INCORRECT_NUMBER_OF_ARGUMENTS = "permissions.incorrect-number-of-arguments";
    }

    public static class COPY
    {

        // copy
        public static final String TITLE = "copy.title";

        // Click to copy value
        public static final String INFO = "copy.info";

        // copy to chat
        public static final String TO_CHAT = "copy.to-chat";

        // copy to clipboard
        public static final String TO_CLIPBOARD = "copy.to-clipboard";
    }

    public static class RESOUREPACK
    {

        // Download to minecraft
        public static final String DOWNLOAD = "resourepack.download";

        // Send download url
        public static final String SEND_URL = "resourepack.send-url";
    }

    public static class COLOR_PICKER
    {

        // Change color
        public static final String CHANGE_COLOR = "color-picker.change-color";

        public static class COMMAND
        {

            // Input custom Hex color in chat
            public static final String DESC_1 = "color-picker.command.desc-1";

            // Or click at the color tile to copy its hex value
            public static final String DESC_3 = "color-picker.command.desc-3";
        }

        public static class GUI
        {

            // Color picker
            public static final String TITLE = "color-picker.gui.title";

            public static class ADD_COLOR
            {

                // Add Color
                public static final String TITLE = "color-picker.gui.add-color.title";

                // Value must be in Hex
                public static final String DESC_1 = "color-picker.gui.add-color.desc-1";
            }
        }
    }
}
