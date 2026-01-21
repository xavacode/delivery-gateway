package com.hawa.constant;

public class OpenApiDocumentationConstant {

    public static final String CONTENT_TYPE = "application/json";
    private static final String INSTANTIATION_ERROR_MESSAGE =
            "Cannot Instantiate";

    private OpenApiDocumentationConstant() {
        throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
    }

    public static class StatusCode {

        public static final String OK = "200";
        public static final String CREATED = "201";
        public static final String NO_CONTENT = "204";
        public static final String BAD_REQUEST = "400";
        public static final String NOT_FOUND = "404";

        private StatusCode() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class ResponseDescription {

        public static final String INVALID_PAYLOAD = "Invalid Payload";
        public static final String NOT_FOUND = "Not Found";
        public static final String DELETED_SUCCESSFULLY =
                "Deleted Successfully";
        public static final String NO_RECORDS_FOUND = "No Records Found";
        public static final String FILE_UPLOADED_SUCCESSFULLY = "File Uploaded Successfully";

        private ResponseDescription() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Restaurant {

        public static final String GET =
                "Paginate or Read All Restaurants with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Restaurant By ID";
        public static final String POST = "Create Restaurant";
        public static final String PUT = "Update Restaurant";
        public static final String DELETE = "Delete Restaurant";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, type, countryId. " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"affiliate name\", \"type\":\"Restaurant Type\", \"countryId\":\"Id of Country\"}";

        public static class Image {
            public static final String POST = "Create Image Required for Restaurant Registration";
        }
        private Restaurant() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Campaign {

        public static final String GET =
                "Paginate or Read All Campaigns with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Campaign By ID";
        public static final String POST = "Create Campaign";
        public static final String PUT = "Update Campaign";
        public static final String DELETE = "Delete Campaign";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, affiliateId, countryId. " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"campaign name\", \"affiliateId\":\"Id of Restaurant\", \"countryId\":\"Id of Country\"}";

        private Campaign() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Category {

        public static final String GET =
                "Paginate or Read All Categories with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Category By ID";
        public static final String POST = "Create Category";
        public static final String PUT = "Update Category";
        public static final String DELETE = "Delete Category";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name. " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"category name\"}";

        private Category() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Club {

        public static final String GET =
                "Paginate or Read All Clubs with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Club By ID";
        public static final String POST = "Create Club";
        public static final String PUT = "Update Club";
        public static final String DELETE = "Delete Club";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, countryId, productId, companyId, gatewayId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"category name\",\"countryId\":\"Id of Country\"," +
                "\"productId\":\"Id of Product\",\"companyId\":\"Id of Company\",\"gatewayId\":\"Id of Gateway\"}";

        private Club() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Company {

        public static final String GET =
                "Paginate or Read All Companies with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Company By ID";
        public static final String POST = "Create Company";
        public static final String PUT = "Update Company";
        public static final String DELETE = "Delete Company";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name. " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"category name\"}";

        private Company() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Country {

        public static final String GET =
                "Paginate or Read All Countries with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Country By ID";
        public static final String POST = "Create Country";
        public static final String PUT = "Update Country";
        public static final String DELETE = "Delete Country";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, isoAlpha2Code, mcc, phonePrefix, timezoneId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"United Kingdom\",\"isoAlpha2Code\":\"UK\"," +
                "\"mcc\":\"234\",\"phonePrefix\":\"44\",\"timezoneId\":\"EUROPE/LONDON\"}";

        private Country() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Currency {

        public static final String GET =
                "Paginate or Read All Currencies with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Currency By ID";
        public static final String POST = "Create Currency";
        public static final String PUT = "Update Currency";
        public static final String DELETE = "Delete Currency";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, code, symbol " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"Pound Sterling\",\"code\":\"GBP\"," +
                "\"symbol\":\"£\"}";

        private Currency() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Domain {

        public static final String GET =
                "Paginate or Read All domains with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Domain By ID";
        public static final String POST = "Create Domain";
        public static final String PUT = "Update Domain";
        public static final String DELETE = "Delete Domain";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, url, type " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"Domain Name\",\"url\":\"abc.xyz.com\"," +
                "\"type\":\"product\"}";

        private Domain() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Gateway {

        public static final String GET =
                "Paginate or Read All gateways with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Gateway By ID";
        public static final String POST = "Create Gateway";
        public static final String PUT = "Update Gateway";
        public static final String DELETE = "Delete Gateway";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, type " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"stripe\",\"type\":\"credit card\"}";

        private Gateway() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class LandingPage {

        public static final String GET =
                "Paginate or Read All Landing Pages with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read LandingPage By ID";
        public static final String POST = "Create LandingPage";
        public static final String PUT = "Update LandingPage";
        public static final String DELETE = "Delete LandingPage";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, categoryId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"landing page name\",\"categoryId\":\"Id of Category\"}";

        private LandingPage() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Offer {

        public static final String GET =
                "Paginate or Read All Offers with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Offer By ID";
        public static final String POST = "Create Offer";
        public static final String PUT = "Update Offer";
        public static final String DELETE = "Delete Offer";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, categoryId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"landing page name\",\"categoryId\":\"Id of Category\"}";

        private Offer() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Operator {

        public static final String GET =
                "Paginate or Read All Operators with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Operator By ID";
        public static final String POST = "Create Operator";
        public static final String PUT = "Update Operator";
        public static final String DELETE = "Delete Operator";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, categoryId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"landing page name\",\"categoryId\":\"Id of Category\"}";

        private Operator() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class PricePlan {

        public static final String GET =
                "Paginate or Read All PricePlans with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read PricePlan By ID";
        public static final String POST = "Create PricePlan";
        public static final String PUT = "Update PricePlan";
        public static final String DELETE = "Delete PricePlan";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, type, clubId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"price plan name\",\"type\":\"subscription\"," +
                "\"clubId\":\"Id of Club\"}";

        private PricePlan() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Product {

        public static final String GET =
                "Paginate or Read All Products with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Product By ID";
        public static final String POST = "Create Product";
        public static final String PUT = "Update Product";
        public static final String DELETE = "Delete Product";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, categoryId, domainId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"product name\",\"categoryId\":\"Id of Category\"," +
                "\"domainId\":\"Id of Domain\"}";

        private Product() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Role {

        public static final String GET =
                "Paginate or Read All Roles with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read Role By ID";
        public static final String POST = "Create Role";
        public static final String PUT = "Update Role";
        public static final String DELETE = "Delete Role";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name." +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"role name\"}";

        private Role() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class User {

        public static final String GET =
                "Paginate or Read All Users with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read User By ID";
        public static final String POST = "Create User";
        public static final String PUT = "Update User";
        public static final String DELETE = "Delete User";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: usernme, email, phoneNumber, roleId." +
                "Should be provided the URL Encoded JSON String e.g {\"username\":\"mr. a\",\"email\":\"abc@xyz.com\"," +
                "\"phoneNumber\":\"123456789\",\"roleId\":\"Id of Role\"}";

        private User() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class BillingPage {

        public static final String GET =
                "Paginate or Read All Billing Pages with Sorting and Filtering";
        public static final String GET_BY_ID =
                "Read BIlling Page By ID";
        public static final String POST = "Create BillingPage";
        public static final String PUT = "Update BillingPage";
        public static final String DELETE = "Delete BillingPage";
        public static final String SORT_PARAMETER_DESCRIPTION="Available sorting parameters: name, createdAt";
        public static final String FILTER_PARAMETER_DESCRIPTION="Available filtering parameters: name, categoryId " +
                "Should be provided the URL Encoded JSON String e.g {\"name\":\"billing page name\",\"categoryId\":\"Id of Category\"}";

        private BillingPage() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Statistics {

        public static final String GET_CLICK_BY_ID =
                "Get Click By ID";
        public static final String CLICKED_DATE_TIME_FORMAT="YYYY-MM-DD HH:MM:SS";

        private Statistics() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }

    public static class Security {

        public static final String LOGIN =
                "Generate Jwt Token";
        public static final String ME="Get Logged In User Details";
        public static final String REFRESH_TOKEN="Refresh Jwt Token";
        public static final String CSRF_TOKEN="Get Csrf Token";

        private Security() {
            throw new AssertionError(INSTANTIATION_ERROR_MESSAGE);
        }
    }
}
