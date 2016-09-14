/*
 *
 * Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries © European Union, 2015-2016.
 *
 * This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package eu.europa.ec.fisheries.ers.service.bean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.fisheries.uvms.activity.model.dto.config.ActivityConfigDTO;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by padhyad on 8/25/2016.
 */
public class PreferenceConfigServiceBeanTest {

    @InjectMocks
    private PreferenceConfigServiceBean preferenceConfigService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String adminConfig = "{\n" +
            "                    \"fishingActivityConfig\": {\n" +
            "                        \"summaryReport\": [\n" +
            "                            \"dataSource\",\n" +
            "                            \"from\",\n" +
            "                            \"startDate\",\n" +
            "                            \"endDate\",\n" +
            "                            \"cfr\",\n" +
            "                            \"ircs\",\n" +
            "                            \"extMark\",\n" +
            "                            \"uvi\",\n" +
            "                            \"iccat\",\n" +
            "                            \"gfcm\",\n" +
            "                            \"purposeCode\",\n" +
            "                            \"reportType\",\n" +
            "                            \"activityType\",\n" +
            "                            \"area\",\n" +
            "                            \"location\",\n" +
            "                            \"gearType\",\n" +
            "                            \"species\"\n" +
            "                        ]\n" +
            "                    }\n" +
            "                }";

    private String userConfig = "{\n" +
            "                    \"fishingActivityConfig\": {\n" +
            "                        \"summaryReport\": [\n" +
            "                            \"dataSource\",\n" +
            "                            \"from\",\n" +
            "                            \"startDate\",\n" +
            "                            \"endDate\",\n" +
            "                            \"purposeCode\",\n" +
            "                            \"reportType\",\n" +
            "                            \"activityType\",\n" +
            "                            \"area\",\n" +
            "                            \"location\",\n" +
            "                            \"gearType\",\n" +
            "                            \"species\"\n" +
            "                        ]\n" +
            "                    }\n" +
            "                }";

    @Test
    public void TestGetAdminConfig() throws Exception {
        ActivityConfigDTO source = getConfiguration(adminConfig);
        ActivityConfigDTO adminConfigDto = preferenceConfigService.getAdminConfig(adminConfig);

        assertEquals(source.getFishingActivityConfig().getSummaryReport(), adminConfigDto.getFishingActivityConfig().getSummaryReport());
    }

    @Test
    public void testGetUserConfig() throws Exception {
        ActivityConfigDTO userConfigDto = preferenceConfigService.getUserConfig(userConfig, adminConfig);
        ActivityConfigDTO admin = getConfiguration(adminConfig);
        ActivityConfigDTO user = getConfiguration(userConfig);

        assertEquals(user.getFishingActivityConfig().getSummaryReport(), userConfigDto.getFishingActivityConfig().getSummaryReport());

    }

    @Test
    public void testSaveAdminConfig() throws Exception {
        ActivityConfigDTO adminConfigDto = getConfiguration(adminConfig);
        String adminJson = preferenceConfigService.saveAdminConfig(adminConfigDto);
        assertEquals(getJson(adminConfigDto), adminJson);
    }

    @Test
    public void testSaveUserConfig() throws Exception {
        String updatedConfig = adminConfig;
        ActivityConfigDTO updatedConfigDto = getConfiguration(updatedConfig);
        String userJson = preferenceConfigService.saveUserConfig(updatedConfigDto, userConfig);
        assertEquals(getJson(updatedConfigDto), userJson);
    }

    @Test
    public void testResetUserConfig() throws Exception {
        String resetConfig = "{\n" +
                "    \"fishingActivityConfig\": {\n" +
                "        \"summaryReport\": [\n" +
                "            \n" +
                "        ]\n" +
                "    }\n" +
                "}";
        ActivityConfigDTO activityConfigDTO = getConfiguration(resetConfig);
        String userJson = preferenceConfigService.resetUserConfig(activityConfigDTO, userConfig);
        ActivityConfigDTO updated = getConfiguration(userJson);
        assertNull(updated.getFishingActivityConfig().getSummaryReport());
    }

    private ActivityConfigDTO getConfiguration(String configString) throws IOException {
        if (configString == null || configString.isEmpty()) {
            return new ActivityConfigDTO();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper.readValue(configString, ActivityConfigDTO.class);
    }

    private String getJson(ActivityConfigDTO config) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(config);
    }
}
