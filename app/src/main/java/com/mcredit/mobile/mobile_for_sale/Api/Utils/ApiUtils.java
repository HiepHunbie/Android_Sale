package com.mcredit.mobile.mobile_for_sale.Api.Utils;


import com.mcredit.mobile.mobile_for_sale.Api.RetrofitClient;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;

/**
 * Created by hiepnt on 05/01/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = AppContants.BaseUrl;

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
