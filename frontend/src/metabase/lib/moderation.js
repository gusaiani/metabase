import { t } from "ttag";

export const MODERATION_ACTIONS = {
  verification: {
    name: "verification",
    icon: "verified",
    color: "brand",
  },
  flag: {
    name: "flag",
    icon: "warning_colorized",
    color: "accent5",
  },
  question: {
    name: "question",
    icon: "clarification",
    color: "accent2",
  },
};

export const MODERATION_TEXT = {
  user: {},
  moderator: {
    verification: {
      action: t`Verify this`,
    },
    flag: {
      action: t`This is misleading`,
    },
    question: {
      action: t`This is confusing`,
    },
  },
};

export function getModerationActionsList() {
  return [
    MODERATION_ACTIONS.verification,
    MODERATION_ACTIONS.flag,
    MODERATION_ACTIONS.question,
  ];
}

export function getModerationStatusIcon(status) {
  const { icon } = MODERATION_ACTIONS[status] || {};
  return icon;
}
