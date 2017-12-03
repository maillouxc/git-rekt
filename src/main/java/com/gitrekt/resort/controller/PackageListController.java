package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Package;

/**
 * Simple little interface for communicating between package list items and the list they are in.
 */
public interface PackageListController {

    void updatePackageQty(Package p, int newValue);

}
