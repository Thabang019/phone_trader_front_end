package za.ac.cput.factory;

import za.ac.cput.domain.Specification;
import za.ac.cput.util.Helper;

public class SpecificationFactory {

    public static Specification createSpecification(double screenSize, String storage, String ram, String operatingSystem, String camera, int numOfSims, String microSD, String fingerPrint, String waterResistance, String wirelessCharging){

        if(Helper.isNullorEmpty(storage) || Helper.isNullorEmpty(ram) || Helper.isNullorEmpty(operatingSystem) || Helper.isNullorEmpty(camera) || Helper.isNullorEmpty(microSD) || Helper.isNullorEmpty(fingerPrint) || Helper.isNullorEmpty(waterResistance) || Helper.isNullorEmpty(wirelessCharging))
            return null;

        return new Specification.Builder()
                .setScreenSize(screenSize)
                .setStorage(storage)
                .setRam(ram)
                .setOperatingSystem(operatingSystem)
                .setCamera(camera)
                .setNumOfSims(numOfSims)
                .setMicroSD(microSD)
                .setFingerPrint(fingerPrint)
                .setWaterResistance(waterResistance)
                .setWirelessCharging(wirelessCharging)
                .build();

    }
}
