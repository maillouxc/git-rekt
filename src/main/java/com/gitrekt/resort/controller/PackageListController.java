package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Package;

public interface PackageListController {

    void updatePackageQty(Package p, int newValue);

}
