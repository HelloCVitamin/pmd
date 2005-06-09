/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class represents a Set of rules.
 * @see Rule
 */
public class RuleSet {

    private Set rules = new HashSet();
    private String name;
    private String description;

	/**
	 * Returns the number of rules in this ruleset
	 * @return an int representing the number of rules
	 */
    public int size() {
        return rules.size();
    }

	/**
	 * Add a new rule to this ruleset
	 * @param rule the rule to be added
	 */
    public void addRule(Rule rule) {
        rules.add(rule);
    }

	/**
	 * Returns the actual Set of rules in this ruleset
	 * @return a Set with the rules. All objects are of type {@link Rule}
	 */
    public Set getRules() {
        return rules;
    }

    /**
     * @return true if any rule in the RuleSet needs the DFA layer
     */
    public boolean usesDFA() {
        for (Iterator i = rules.iterator(); i.hasNext();) {
            Rule r = (Rule) i.next();
            if (r.usesDFA()) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Returns the Rule with the given name
	 *
	 * @param ruleName the name of the rule to find
	 * @return
	 * @throws RuntimeException when the rule with the given name cannot be found
	 */
    public Rule getRuleByName(String ruleName) {
        for (Iterator i = rules.iterator(); i.hasNext();) {
            Rule r = (Rule) i.next();
            if (r.getName().equals(ruleName)) {
                return r;
            }
        }
        throw new RuntimeException("Couldn't find rule named " + ruleName + " in the ruleset " + name);
    }

	/**
	 * Add a whole RuleSet to this RuleSet
	 *
	 * @param ruleSet the RuleSet to add
	 */
    public void addRuleSet(RuleSet ruleSet) {
        rules.addAll(ruleSet.getRules());
    }

    public void apply(List acuList, RuleContext ctx) {
        Iterator rs = rules.iterator();
        while (rs.hasNext()) {
            Rule rule = (Rule) rs.next();
            rule.apply(acuList, ctx);
        }
    }

	/**
	 * Gives the name of this ruleset
	 *
	 * @return a String representing the name
	 */
    public String getName() {
        return name;
    }

	/**
	 * Set the name of this ruleset
	 *
	 * @param name a String representing the name
	 */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * Gives the description of this ruleset
	 *
	 * @return a String representing the description
	 */
    public String getDescription() {
        return description;
    }

	/**
	 * Set the description of this ruleset
	 *
	 * @param description a String representing the description
	 */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if ((o == null) || !(o instanceof RuleSet)) {
            return false; // Trivial
        }
        
        if (this == o) {
            return true; // Basic equality
        }
        
        RuleSet ruleSet = (RuleSet) o;
        return this.getName().equals(ruleSet.getName()) && this.getRules().equals(ruleSet.getRules()); 
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.getName().hashCode() + 13*this.getRules().hashCode();
    }
}
