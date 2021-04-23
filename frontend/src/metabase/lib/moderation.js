import { t } from "ttag";

export const MODERATION_ACTIONS = {
  verification: {
    type: "verification",
    icon: "verified",
    color: "brand",
  },
  flag: {
    type: "flag",
    icon: "warning_colorized",
    color: "accent5",
  },
  question: {
    type: "question",
    icon: "clarification",
    color: "accent2",
  },
};

export const MODERATION_TEXT = {
  user: {},
  moderator: {
    verification: {
      action: t`Verify this`,
      actionCreationDescription: t`Everything look correct here? Verify this question to let others know.`,
      actionCreationLabel: t`Add a note if you’d like`,
      actionCreationButton: t`Verify`,
    },
    flag: {
      action: t`This is misleading`,
      actionCreationDescription: t`Add a warning badge to this question and notify its editors that something’s off here.`,
      actionCreationLabel: t`Explain what’s wrong or misleading`,
      actionCreationButton: t`Flag as misleading`,
    },
    question: {
      action: t`This is confusing`,
      actionCreationDescription: "abc",
      actionCreationLabel: "123",
      actionCreationButton: t`foo`,
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

export function getModerationStatusIcon(type) {
  const { icon } = MODERATION_ACTIONS[type] || {};
  return icon;
}

export function getColor(type) {
  const { color } = MODERATION_ACTIONS[type] || {};
  return color;
}
