import React from "react";
import SidebarContent from "metabase/query_builder/components/SidebarContent";
import { PLUGIN_MODERATION_COMPONENTS } from "metabase/plugins";

const {
  active: isPluginActive,
  ModerationIssueActionMenu,
} = PLUGIN_MODERATION_COMPONENTS;

function QuestionDetailsSidebar() {
  return (
    <SidebarContent className="full-height px1">
      {isPluginActive ? (
        <div>
          <ModerationIssueActionMenu onAction={() => {}} />
        </div>
      ) : (
        <div />
      )}
    </SidebarContent>
  );
}

export default QuestionDetailsSidebar;
