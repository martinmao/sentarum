/**
 * Copyright 2001-2005 The Apache Software Foundation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.core.model.primitive;

/**
 * domain primitive of geo
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Geo {

    private LonLat lonLat;

    public Geo() {
    }

    public Geo(LonLat lonLat) {
        this.lonLat = lonLat;
    }


    public Geo(double latitude, double longitude) {
        this(new LonLat(latitude, longitude));
    }


    public LonLat getLonLat() {
        return lonLat;
    }

    public void setLonLat(LonLat lonLat) {
        this.lonLat = lonLat;
    }

    /**
     * represent latitude and longitude
     */
    public static class LonLat {
        /**
         * Minimum longitude value.
         */
        public static final double MIN_LON_INCL = -180.0D;

        /**
         * Maximum longitude value.
         */
        public static final double MAX_LON_INCL = 180.0D;

        /**
         * Minimum latitude value.
         */
        public static final double MIN_LAT_INCL = -90.0D;

        /**
         * Maximum latitude value.
         */
        public static final double MAX_LAT_INCL = 90.0D;


        private final Double latitude;


        private final Double longitude;

        /**
         * constructor geo using given latitude and longitude.
         *
         * @param latitude
         * @param longitude
         */
        public LonLat(Double latitude, Double longitude) {
            this.latitude = latitude;
            assertLatitude(latitude);
            this.longitude = longitude;
            assertLongitude(longitude);
        }

        /**
         * return true if given latitude is valid.
         *
         * @param latitude
         * @return
         */
        public static boolean isValidLatitude(Double latitude) {
            if (Double.isNaN(latitude) || latitude < MIN_LAT_INCL || latitude > MAX_LAT_INCL) {
                return false;
            }
            return true;
        }

        /**
         * return true if given longitude is valid.
         *
         * @param longitude
         * @return
         */
        public static boolean isValidLongitude(Double longitude) {
            if (Double.isNaN(longitude) || longitude < MIN_LON_INCL || longitude > MAX_LON_INCL) {
                return false;
            }
            return true;
        }

        /**
         * validates latitude value is within standard +/-90 coordinate bounds
         */
        public static void assertLatitude(double latitude) {
            if (!isValidLatitude(latitude)) {
                throw new IllegalArgumentException("invalid latitude " + latitude + "; must be between " + MIN_LAT_INCL + " and " + MAX_LAT_INCL);
            }
        }

        /**
         * validates longitude value is within standard +/-180 coordinate bounds
         */
        public static void assertLongitude(double longitude) {
            if (!isValidLatitude(longitude)) {
                throw new IllegalArgumentException("invalid longitude " + longitude + "; must be between " + MIN_LON_INCL + " and " + MAX_LON_INCL);
            }
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }
    }


}
