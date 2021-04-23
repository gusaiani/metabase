import React from "react";
import SidebarContent from "metabase/query_builder/components/SidebarContent";
import { PLUGIN_MODERATION_ISSUE_ACTION_MENU } from "metabase/plugins";

function QuestionDetailsSidebar() {
  return (
    <SidebarContent className="full-height px1">
      <div>
        {PLUGIN_MODERATION_ISSUE_ACTION_MENU.Component && (
          <PLUGIN_MODERATION_ISSUE_ACTION_MENU.Component />
        )}
      </div>
    </SidebarContent>
  );
}

export default QuestionDetailsSidebar;
