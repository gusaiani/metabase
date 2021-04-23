import React from "react";
import { t } from "ttag";
import PropTypes from "prop-types";

import EntityMenu from "metabase/components/EntityMenu";

function ModerationIssueActionMenu({ className }) {
  return (
    <EntityMenu
      triggerChildren={t`Moderate`}
      triggerProps={{
        iconRight: "chevrondown",
        round: true,
        className: "text-brand border-brand",
      }}
      className={className}
      items={[
        {
          icon: "verified",
          iconSize: 18,
          title: t`Verify this`,
          className: "text-brand",
          action: () => null,
        },
        {
          icon: "warning_colorized",
          iconSize: 18,
          title: t`This is misleading`,
          className: "text-accent5",
          action: () => null,
        },
        {
          icon: "clarification",
          iconSize: 18,
          title: t`This is confusing`,
          className: "text-accent2",
          action: () => null,
        },
      ]}
    />
  );
}

ModerationIssueActionMenu.propTypes = {
  className: PropTypes.string,
};

export default ModerationIssueActionMenu;
