package com.gitrekt.resort.hibernate;

import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;
import com.github.fluent.hibernate.cfg.strategy.hibernate5.Hibernate5NamingStrategy;

public class SnakeCaseNamingStrategy extends
        Hibernate5NamingStrategy {
    
    public SnakeCaseNamingStrategy() {
        super();
        StrategyOptions options = new StrategyOptions();
        options.setColumnPrefix("");
        super.setOptions(options);
    }
    
}
