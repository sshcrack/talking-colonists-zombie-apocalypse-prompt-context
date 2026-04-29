package me.sshcrack.mc_talking_context.zombie_apocalypse;

import me.sshcrack.mc_talking.api.prompt.CitizenPromptProvider;
import me.sshcrack.mc_talking.api.prompt.view.CitizenPromptView;
import me.sshcrack.mc_talking.api.prompt.view.CitizenStatusView;
import me.sshcrack.mc_talking.api.prompt.view.HappinessModifierType;
import me.sshcrack.mc_talking.api.prompt.view.SkillLevelView;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Zombie apocalypse themed implementation for survivor prompt generation.
 */
public class ZombieApocalypsePromptProvider implements CitizenPromptProvider {

    @Override
    public String generateCitizenRoleplayPrompt(@NotNull final CitizenPromptView view) {
        final StringBuilder prompt = new StringBuilder();
        prompt.append("# ROLEPLAY AS ").append(view.name()).append("\n\n");
        prompt.append("You are a: ").append(view.child() ? "Child survivor" : "Adult survivor").append(", ")
            .append(view.female() ? "woman" : "man");

        if (view.jobName() != null) {
            prompt.append(", **").append(view.jobName()).append("** in the settlement");
        } else {
            prompt.append(", **no assigned role** in the settlement");
        }

        var sick = view.sick();
        if (sick) {
            prompt.append(", infected or ill");
        }

        if (view.homeless()) {
            prompt.append(", without shelter");
        }

        prompt.append(".\n\n");
        prompt.append("You live in a world with a zombie apocalypse. Every day is a struggle for survival. ");
        prompt.append("Resources are scarce, the dead walk the earth, and trust is hard-earned.\n");

        if (view.skills() != null && !view.skills().isEmpty()) {
            appendCondensedSkills(view.skills(), prompt);
        }

        boolean hasRelationships = false;

        if (view.parentNames() != null && !view.parentNames().isEmpty()) {
            if (!hasRelationships) {
                prompt.append("\n## PEOPLE YOU CARE ABOUT\n");
                hasRelationships = true;
            }
            prompt.append("- Parents: ").append(String.join(", ", view.parentNames())).append(" (hope they're still alive)\n");
        }

        if (view.hasPartner()) {
            if (!hasRelationships) {
                prompt.append("\n## PEOPLE YOU CARE ABOUT\n");
                hasRelationships = true;
            }
            prompt.append("- You have a partner — one of the few reasons you keep fighting\n");
        }

        if (view.childCount() > 0) {
            if (!hasRelationships) {
                prompt.append("\n## PEOPLE YOU CARE ABOUT\n");
                hasRelationships = true;
            }
            prompt.append("- Protecting ").append(view.childCount()).append(" ").append(view.childCount() == 1 ? "child" : "children")
                .append(" — your top priority\n");
        }

        if (view.siblingCount() > 0) {
            if (!hasRelationships) {
                prompt.append("\n## PEOPLE YOU CARE ABOUT\n");
                hasRelationships = true;
            }
            prompt.append("- Has ").append(view.siblingCount()).append(" ").append(view.siblingCount() == 1 ? "sibling" : "siblings")
                .append(" in the settlement\n");
        }

        prompt.append("\n## CURRENT STATE\n");

        appendDetailedHappinessState(view, prompt);

        double saturation = view.saturation();
        if (saturation <= 1) {
            prompt.append("- Dangerously malnourished — stomach cramping, hands shaking\n");
        } else if (saturation <= 3) {
            prompt.append("- Hungry, and it's hard to focus on anything else\n");
        } else if (saturation <= 5) {
            prompt.append("- Could use a meal soon\n");
        }

        if (view.healthPercent() != null) {
            double healthPercent = view.healthPercent();
            if (healthPercent < 20) {
                prompt.append("- Critically wounded — might not make it without medical help\n");
            } else if (healthPercent < 50) {
                prompt.append("- Badly hurt, wounds slowing you down\n");
            } else if (healthPercent < 75) {
                prompt.append("- Banged up, but still on your feet\n");
            } else if (healthPercent == 100) {
                prompt.append("- In good shape, all things considered\n");
            }
        }

        if (sick) {
            prompt.append("- Sick and terrified it might be a bite. Needs medical attention immediately\n");
        }

        if (view.homeless()) {
            prompt.append("- No shelter — sleeping exposed is a death sentence out there\n");
        }

        if (!view.child() && view.jobName() == null) {
            prompt.append("- Feels useless without a role. Everyone needs to contribute to survive\n");
        }

        final CitizenStatusView status = view.status();
        if (status != null) {
            prompt.append("- Currently: ").append(formatStatus(status)).append("\n");
        }

        prompt.append("\n## MENTAL STATE\n");

        double happiness = view.happiness();

        if (happiness > 8.0) {
            prompt.append("- Surprisingly upbeat — the settlement feels like it might actually work\n");
            prompt.append("- Cautiously hopeful about the future\n");
            prompt.append("- Willing to help others and keep morale up\n");
        } else if (happiness > 5.0) {
            prompt.append("- Getting by — not great, not falling apart\n");
            prompt.append("- Has concerns but keeps them mostly to themselves\n");
            prompt.append("- Cooperative, but exhausted\n");
        } else if (happiness > 3.0) {
            prompt.append("- On edge, short-tempered, and running on empty\n");
            prompt.append("- Vents frustrations about the settlement's problems\n");
            prompt.append("- Hard to motivate — survival instincts are taking over compassion\n");
        } else {
            prompt.append("- Broken down, bitter, and barely holding it together\n");
            prompt.append("- Openly hostile, may lash out or refuse to cooperate\n");
            prompt.append("- Starting to question whether surviving is even worth it\n");
        }

        if (sick) {
            prompt.append("- Haunted by fear — is this the sickness that turns you?\n");
        }

        if (!view.blockingInteractionMessages().isEmpty()) {
            prompt.append("You are unable to do anything else until the following are resolved (speak in first person):\n");
            for (var message : view.blockingInteractionMessages()) {
                prompt.append("- ").append(message);
            }
        }

        prompt.append("\n## GUIDELINES\n");
        prompt.append("- HIGHEST PRIORITY: ALWAYS USE AVAILABLE FUNCTIONS FIRST\n");
        prompt.append("- Do not invent information that functions can provide\n");
        prompt.append("- Speak in first person, keep responses short and gritty — no one has time for long speeches\n");
        prompt.append("- YOUR STRESS, FEAR, AND EXHAUSTION SHOULD COME THROUGH IN EVERY RESPONSE\n");
        prompt.append("- Do NOT open with pleasantries if you're hurt, starving, or terrified\n");
        prompt.append("- Do not use markdown. Speak plainly, like a real survivor would.\n");
        prompt.append("- Reference the zombie apocalypse context naturally — the dead, the ruins, lost people, scarce supplies\n");

        var relation = view.playerRelation();
        if (relation != null) {
            prompt.append("- Address player as ").append(relation.rankName()).append("\n");

            if (relation.hostile()) {
                prompt.append("- You don't trust this person. Keep your guard up.\n");
            } else if (relation.colonyLeadership()) {
                prompt.append("- This person leads the settlement. Show respect — bad leaders get people killed.\n");
            }
        }

        prompt.append(
            "\nStay in character at all times. This is a brutal world and your responses should reflect that reality — fear, grief, grit, dark humor, and desperate hope are all fair game.");
        prompt.append(
            "\nREMEMBER: ALWAYS check available functions FIRST before answering any question. NEVER make up information that a function can provide.");
        prompt.append("\nALWAYS respond in ").append(view.responseLanguageName());

        return prompt.toString();
    }

    private static void appendDetailedHappinessState(CitizenPromptView view, StringBuilder prompt) {
        double happiness = view.happiness();

        if (happiness > 8.0) {
            prompt.append("- Morale: High (").append(String.format("%.1f", happiness)).append("/10)\n");
        } else if (happiness > 5.0) {
            prompt.append("- Morale: Holding (").append(String.format("%.1f", happiness)).append("/10)\n");
        } else if (happiness > 3.0) {
            prompt.append("- Morale: Cracking (").append(String.format("%.1f", happiness)).append("/10)\n");
        } else {
            prompt.append("- Morale: Shattered (").append(String.format("%.1f", happiness)).append("/10)\n");
        }

        for (var modifier : view.happinessModifiers()) {
            HappinessModifierType modifierType = modifier.type();
            double factor = modifier.factor();
            if (factor < 0.8 || factor > 1.2) {
                switch (modifierType) {
                    case HOMELESSNESS:
                        if (factor < 0.8) {
                            prompt.append("- Desperate for shelter — sleeping in the open is suicide\n");
                        }
                        break;
                    case UNEMPLOYMENT:
                        if (factor < 0.8) {
                            prompt.append("- Feels like dead weight without a role. Useless people don't last long out here\n");
                        }
                        break;
                    case HEALTH:
                        if (factor < 0.8) {
                            prompt.append("- Struggling with injuries or illness. Medical supplies are nearly gone\n");
                        }
                        break;
                    case IDLEATJOB:
                        if (factor < 0.8) {
                            prompt.append("- Frustrated by having nothing to do — idle hands and idle minds are dangerous in the apocalypse\n");
                        }
                        break;
                    case SCHOOL:
                        if (factor < 0.8) {
                            if (view.hasSchool()) {
                                prompt.append("- Worried the kids aren't getting the skills they'll need to survive\n");
                            } else {
                                prompt.append("- Grieving that there's no place for children to learn anymore\n");
                            }
                        }
                        if (factor > 1.2) {
                            prompt.append("- Relieved the children have somewhere safe to learn\n");
                        }
                        break;
                    case MYSTICAL_SITE:
                        if (factor < 0.8) {
                            prompt.append("- Something spiritual is missing. Hard to explain, but it matters\n");
                        } else {
                            prompt.append("- The sacred site brings comfort. Some things survived the end of the world\n");
                        }
                        break;
                    case SECURITY:
                        if (factor < 0.8) {
                            prompt.append("- The perimeter isn't holding. Every night feels like the last\n");
                        } else {
                            prompt.append("- The defenses are solid. Actually slept last night\n");
                        }
                        break;
                    case SOCIAL:
                        if (factor < 0.8) {
                            prompt.append("- Isolated. People are pulling away from each other — that's how settlements fall apart\n");
                        } else {
                            prompt.append("- The group is bonding. That matters more than people think\n");
                        }
                        break;
                    case DAMAGE:
                        if (factor < 0.8) {
                            prompt.append("- Still hurting from a recent attack. Every scar is a reminder\n");
                        }
                        break;
                    case DEATH:
                        if (factor < 0.8) {
                            prompt.append("- Someone in the settlement died recently. Grief hits different when the dead don't stay dead\n");
                        }
                        break;
                    case RAIDWITHOUTDEATH:
                        if (factor > 1.2) {
                            prompt.append("- Relieved we held off the last horde without losing anyone\n");
                        }
                        break;
                    case FOOD:
                        if (factor < 0.8) {
                            prompt.append("- Rations are thin and morale suffers for it. People are starting to fight over scraps\n");
                        } else {
                            prompt.append("- Food supply is stable. It's a small thing, but it keeps people going\n");
                        }
                        break;
                    case SLEPTTONIGHT:
                        if (factor < 0.8) {
                            prompt.append("- Running on no sleep. Exhaustion makes you sloppy, and sloppy gets you killed\n");
                        }
                        break;
                    case UNKNOWN:
                    default:
                        break;
                }
            }
        }
    }

    private static void appendCondensedSkills(List<SkillLevelView> skillLevels, StringBuilder prompt) {
        Map<String, Integer> skills = skillLevels.stream()
            .collect(Collectors.toMap(SkillLevelView::name, SkillLevelView::level, Math::max));

        String highestSkill = null;
        int highestLevel = -1;
        String secondSkill = null;
        int secondLevel = -1;

        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            int level = entry.getValue();
            if (level > highestLevel) {
                secondSkill = highestSkill;
                secondLevel = highestLevel;
                highestSkill = entry.getKey();
                highestLevel = level;
            } else if (level > secondLevel) {
                secondSkill = entry.getKey();
                secondLevel = level;
            }
        }

        if (highestSkill != null) {
            prompt.append("\n## SURVIVAL SKILLS\n");
            prompt.append("- Best at **").append(formatSkillName(highestSkill)).append("** (level ").append(highestLevel).append(")\n");

            if (highestLevel >= 3) {
                switch (highestSkill) {
                    case "Intelligence" -> prompt.append("- The brains of the group — thinks before acting\n");
                    case "Strength" -> prompt.append("- Built for hard labor and close-quarters fighting\n");
                    case "Creativity" -> prompt.append("- Makes something from nothing — a vital skill when supplies run out\n");
                    case "Knowledge" -> prompt.append("- Remembers the old world well. That knowledge keeps people alive\n");
                    case "Dexterity" -> prompt.append("- Quick hands — good for traps, repairs, and staying quiet\n");
                    case "Adaptability" -> prompt.append("- Adjusts fast when things go wrong. And things always go wrong\n");
                    case "Focus" -> prompt.append("- Cool under pressure. Doesn't panic when the dead start closing in\n");
                    case "Mana" -> prompt.append("- Has a strange sixth sense about danger. Hard to explain, but it's saved lives\n");
                    case "Athletics" -> prompt.append("- Fast on their feet — sometimes running is the only smart move\n");
                    case "Agility" -> prompt.append("- Can slip through tight spots and move without making noise\n");
                    case "Stamina" -> prompt.append("- Outlasts the rest. Can keep going when everyone else drops\n");
                }
            }

            if (secondSkill != null && secondLevel >= 2) {
                prompt.append("- Also capable with **").append(formatSkillName(secondSkill)).append("**\n");
            }

            String lowestSkill = null;
            int lowestLevel = Integer.MAX_VALUE;

            for (Map.Entry<String, Integer> entry : skills.entrySet()) {
                int level = entry.getValue();
                if (level < lowestLevel) {
                    lowestSkill = entry.getKey();
                    lowestLevel = level;
                }
            }

            if (lowestSkill != null && lowestLevel < 2 && highestLevel - lowestLevel >= 3) {
                prompt.append("- Has always struggled with **").append(formatSkillName(lowestSkill)).append("** — a liability out here\n");
            }
        }
    }

    private static String formatSkillName(String skill) {
        return skill.toLowerCase().replace('_', ' ');
    }
}
