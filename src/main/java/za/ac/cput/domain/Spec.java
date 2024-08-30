package za.ac.cput.domain;

import java.util.Objects;

public class Spec {

    private double screenSize;
    private String storage;
    private String ram;
    private String operatingSystem;
    private String camera;
    private int numOfSims;
    private String microSD;
    private String fingerPrint;
    private String waterResistance;
    private String wirelessCharging;

    protected Spec() {
    }

    private Spec(Builder builder ) {

        this.screenSize = builder.screenSize;
        this.storage = builder.storage;
        this.ram = builder.ram;
        this.operatingSystem = builder.operatingSystem;
        this.camera = builder.camera;
        this.numOfSims = builder.numOfSims;
        this.microSD = builder.microSD;
        this.fingerPrint = builder.fingerPrint;
        this.waterResistance = builder.waterResistance;
        this.wirelessCharging = builder.wirelessCharging;
    }



    public double getScreenSize() {
        return screenSize;
    }

    public String getStorage() {
        return storage;
    }

    public String getRam() {
        return ram;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getCamera() {
        return camera;
    }

    public int getNumOfSims() {
        return numOfSims;
    }

    public String getMicroSD() {
        return microSD;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public String getWaterResistance() {
        return waterResistance;
    }

    public String getWirelessCharging() {
        return wirelessCharging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spec that = (Spec) o;
        return Double.compare(screenSize, that.screenSize) == 0 && numOfSims == that.numOfSims  && Objects.equals(storage, that.storage) && Objects.equals(ram, that.ram) && Objects.equals(operatingSystem, that.operatingSystem) && Objects.equals(camera, that.camera) && Objects.equals(microSD, that.microSD) && Objects.equals(fingerPrint, that.fingerPrint) && Objects.equals(waterResistance, that.waterResistance) && Objects.equals(wirelessCharging, that.wirelessCharging);
    }

    @Override
    public int hashCode() {
        return Objects.hash( screenSize, storage, ram, operatingSystem, camera, numOfSims, microSD, fingerPrint, waterResistance, wirelessCharging);
    }

    @Override
    public String toString() {
        return "Spec{" +

                ", screenSize=" + screenSize +
                ", storage='" + storage + '\'' +
                ", ram='" + ram + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", camera='" + camera + '\'' +
                ", numOfSims=" + numOfSims +
                ", microSD='" + microSD + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                ", waterResistance='" + waterResistance + '\'' +
                ", wirelessCharging='" + wirelessCharging + '\'' +
                '}';
    }


    public static class Builder {


        private double screenSize;
        private String storage;
        private String ram;
        private String operatingSystem;
        private String camera;
        private int numOfSims;
        private String microSD;
        private String fingerPrint;
        private String waterResistance;
        private String wirelessCharging;



        public Builder setScreenSize(double screenSize) {
            this.screenSize = screenSize;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setOperatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Builder setCamera(String camera) {
            this.camera = camera;
            return this;
        }

        public Builder setNumOfSims(int numOfSims) {
            this.numOfSims = numOfSims;
            return this;
        }

        public Builder setMicroSD(String microSD) {
            this.microSD = microSD;
            return this;
        }

        public Builder setFingerPrint(String fingerPrint) {
            this.fingerPrint = fingerPrint;
            return this;
        }

        public Builder setWaterResistance(String waterResistance) {
            this.waterResistance = waterResistance;
            return this;
        }

        public Builder setWirelessCharging(String wirelessCharging) {
            this.wirelessCharging = wirelessCharging;
            return this;
        }

        public Builder copy(Spec specification){

            this.screenSize = specification.screenSize;
            this.storage = specification.storage;
            this.ram = specification.ram;
            this.operatingSystem = specification.operatingSystem;
            this.camera = specification.camera;
            this.numOfSims = specification.numOfSims;
            this.microSD = specification.microSD;
            this.fingerPrint = specification.fingerPrint;
            this.waterResistance = specification.waterResistance;
            this.wirelessCharging = specification.wirelessCharging;
            return this;
        }

        public Spec build(){
            return new Spec(this);
        }
    }
}
