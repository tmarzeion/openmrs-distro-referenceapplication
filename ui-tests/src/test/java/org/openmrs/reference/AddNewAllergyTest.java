/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.reference;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openmrs.reference.groups.BuildTests;
import org.openmrs.reference.page.ActiveVisitsPage;
import org.openmrs.reference.page.AddOrEditAllergyPage;
import org.openmrs.reference.page.AllergyPage;
import org.openmrs.reference.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.uitestframework.test.TestData;

public class AddNewAllergyTest extends ReferenceApplicationTestBase {

    private TestData.PatientInfo patient;

    private static final String VISIT_TYPE_UUID = "7b0f5697-27e3-40c4-8bae-f4049abfb4ed";
    private static final String LOCATION_UUID = "8d6c993e-c2cc-11de-8d13-0010c6dffd0f";
    private static final String DRUG_NAME = "Aspirin";

    @Before
    public void setUp() throws Exception {
        patient = createTestPatient();
        createTestVisit();
    }

    @Test
    @Category(BuildTests.class)
    public void addNewAllergyTest() throws Exception {
        ActiveVisitsPage activeVisitsPage = homePage.goToActiveVisitsSearch();
        activeVisitsPage.search(patient.identifier);
        ClinicianFacingPatientDashboardPage patientDashboardPage = activeVisitsPage.goToPatientDashboardOfLastActiveVisit();

        AllergyPage allergyPage = patientDashboardPage.clickOnAllergyManagement();

        AddOrEditAllergyPage addOrEditAllergyPage = allergyPage.clickOnAddNewAllergy();
        addOrEditAllergyPage.enterDrug(DRUG_NAME);
        addOrEditAllergyPage.drugId();
        allergyPage = addOrEditAllergyPage.clickOnSaveAllergy();

        assertTrue(allergyPage.getAllergen().contains(DRUG_NAME));
    }

    @After
    public void tearDown() throws Exception {
        deletePatient(patient.uuid);
    }

    private void createTestVisit(){
        new TestData.TestVisit(patient.uuid, VISIT_TYPE_UUID, LOCATION_UUID).create();
    }

}
