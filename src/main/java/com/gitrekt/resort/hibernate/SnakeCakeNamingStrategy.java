package com.gitrekt.resort.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * This naming strategy can be used by Hibernate to do snake case naming
 * automatically, instead of having to manually specify each column and table
 * name.
 * 
 * This class isn't actually implemented yet, I'll deal with it later.
 */
public class SnakeCakeNamingStrategy extends ImprovedNamingStrategy {
    
    // Because every plural is just adding an s lol.
    // God forbid we need an octopus table.
    public static final String PLURAL_SUFFIX = "s";
    
    @Override
    public String classToTableName(String className) {
        String tableNameInSingularForm = super.classToTableName(className);
        return transformToPluralForm(tableNameInSingularForm);
    }

    public String transformToPluralForm(String tableNameInSingularForm) {
        StringBuilder pluralForm = new StringBuilder();

        pluralForm.append(tableNameInSingularForm);
        pluralForm.append(PLURAL_SUFFIX);
        
        return pluralForm.toString();
    }
}
