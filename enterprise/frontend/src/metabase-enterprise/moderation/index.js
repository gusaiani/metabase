import { PLUGIN_MODERATION_COMPONENTS } from "metabase/plugins";
import ModerationIssueActionMenu from "metabase-enterprise/moderation/components/ModerationIssueActionMenu";
import CreateModerationIssuePanel from "metabase-enterprise/moderation/components/CreateModerationIssuePanel";

Object.assign(PLUGIN_MODERATION_COMPONENTS, {
  active: true,
  ModerationIssueActionMenu,
  CreateModerationIssuePanel,
});
