package com.gitrekt.resort.hibernate;

import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;
import com.github.fluent.hibernate.cfg.strategy.hibernate5.Hibernate5NamingStrategy;

/**
 * This class exists because Hibernate by default uses the exact variable name (in camelCase) to
 * create its table and column names in the SQL database.
 *
 * SQL is not required to be case
 * sensitive, so snake_case is traditionally use. I found a library that promised to do this, but
 * it turns out that they add a bunch of stupid hungarian notation style prefixes to all of the
 * names they generate, so I had to override their implementation to stop that nonsense.
 */
public class SnakeCaseNamingStrategy extends Hibernate5NamingStrategy {

    public SnakeCaseNamingStrategy() {
        super();
        
        StrategyOptions options = new StrategyOptions();
        options.setColumnPrefix("");
        super.setOptions(options);
    }

}
