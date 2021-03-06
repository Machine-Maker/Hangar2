package me.minidigger.hangar.config;

import me.minidigger.hangar.model.Color;
import me.minidigger.hangar.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "hangar")
@ComponentScan("me.minidigger.hangar")
public class HangarConfig {

    private String logo = "https://paper.readthedocs.io/en/latest/_images/papermc_logomark_500.png";
    private String service = "Hangar";
    private List<Sponsor> sponsors;

    private boolean debug = false;
    private int debugLevel = 3;
    private boolean staging = true;
    private boolean logTimings = false;
    private String authUrl = "https://hangarauth.minidigger.me";

    @NestedConfigurationProperty
    public final FakeUserConfig fakeUser;
    @NestedConfigurationProperty
    public HangarHomepageConfig homepage;
    @NestedConfigurationProperty
    public HangarChannelsConfig channels;
    @NestedConfigurationProperty
    public HangarPagesConfig pages;
    @NestedConfigurationProperty
    public HangarProjectsConfig projects;
    @NestedConfigurationProperty
    public HangarUserConfig user;
    @NestedConfigurationProperty
    public HangarOrgConfig org;
    @NestedConfigurationProperty
    public HangarApiConfig api;
    @NestedConfigurationProperty
    public HangarSsoConfig sso;
    @NestedConfigurationProperty
    public HangarSecurityConfig security;
    @NestedConfigurationProperty
    public HangarQueueConfig queue;

    @Component
    public static class Sponsor {
        private String name;
        private String image;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    @Autowired
    public HangarConfig(FakeUserConfig fakeUser, HangarHomepageConfig homepage, HangarChannelsConfig channels, HangarPagesConfig pages, HangarProjectsConfig projects, HangarUserConfig user, HangarOrgConfig org, HangarApiConfig api, HangarSsoConfig sso, HangarSecurityConfig security) {
        this.fakeUser = fakeUser;
        this.homepage = homepage;
        this.channels = channels;
        this.pages = pages;
        this.projects = projects;
        this.user = user;
        this.org = org;
        this.api = api;
        this.sso = sso;
        this.security = security;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(int debugLevel) {
        this.debugLevel = debugLevel;
    }

    public boolean isStaging() {
        return staging;
    }

    public void setStaging(boolean staging) {
        this.staging = staging;
    }

    public boolean isLogTimings() {
        return logTimings;
    }

    public void setLogTimings(boolean logTimings) {
        this.logTimings = logTimings;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    @Component
    @ConfigurationProperties(prefix = "fake-user")
    public static class FakeUserConfig {

        private boolean enabled = true;
        private long id = -1;
        private String name = "paper";
        private String username = "paper";
        private String email = "paper@papermc.io";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.channels")
    public static class HangarChannelsConfig {
        private int maxNameLen = 15;
        private String nameRegex = "^[a-zA-Z0-9]+$";
        @Value("#{T(me.minidigger.hangar.model.Color).getById(${hangar.channels.color-default})}")
        private Color colorDefault = Color.getById(7);
        private String nameDefault = "Release";

        public int getMaxNameLen() {
            return maxNameLen;
        }

        public void setMaxNameLen(int maxNameLen) {
            this.maxNameLen = maxNameLen;
        }

        public String getNameRegex() {
            return nameRegex;
        }

        public void setNameRegex(String nameRegex) {
            this.nameRegex = nameRegex;
        }

        public Color getColorDefault() {
            return colorDefault;
        }

        public void setColorDefault(Color colorDefault) {
            this.colorDefault = colorDefault;
        }

        public String getNameDefault() {
            return nameDefault;
        }

        public void setNameDefault(String nameDefault) {
            this.nameDefault = nameDefault;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.homepage")
    public static class HangarHomepageConfig {
        private String updateInterval = "10m";

        public String getUpdateInterval() {
            return updateInterval;
        }

        public void setUpdateInterval(String updateInterval) {
            this.updateInterval = updateInterval;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.pages")
    public static class HangarPagesConfig {

        @NestedConfigurationProperty
        public Home home;
        private int minLen = 15;
        private int maxLen = 32000;
        @NestedConfigurationProperty
        public Page page;

        @Autowired
        public HangarPagesConfig(Home home, Page page) {
            this.home = home;
            this.page = page;
        }

        @Component
        @ConfigurationProperties(prefix = "hangar.pages.home")
        public static class Home {
            private String name = "Home";
            private String message = "Welcome to your new project!";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }

        @Component
        @ConfigurationProperties(prefix = "hangar.pages.page")
        public static class Page {
            private int maxLen = 75000;

            public int getMaxLen() {
                return maxLen;
            }

            public void setMaxLen(int maxLen) {
                this.maxLen = maxLen;
            }
        }

        public Home getHome() {
            return home;
        }

        public void setHome(Home home) {
            this.home = home;
        }

        public int getMinLen() {
            return minLen;
        }

        public void setMinLen(int minLen) {
            this.minLen = minLen;
        }

        public int getMaxLen() {
            return maxLen;
        }

        public void setMaxLen(int maxLen) {
            this.maxLen = maxLen;
        }

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.projects")
    public static class HangarProjectsConfig {
        private int maxNameLen = 25;
        private int maxPages = 50;
        private int maxChannels = 5;
        private int initLoad = 25;
        private int initVersionLoad = 10;
        private int maxDescLen = 120;
        private boolean fileValidate = true;
        private String staleAge = "28d";
        private String checkInterval = "1h";
        private String draftExpire = "1d";
        private int userGridPageSize = 30;

        public int getMaxNameLen() {
            return maxNameLen;
        }

        public void setMaxNameLen(int maxNameLen) {
            this.maxNameLen = maxNameLen;
        }

        public int getMaxPages() {
            return maxPages;
        }

        public void setMaxPages(int maxPages) {
            this.maxPages = maxPages;
        }

        public int getMaxChannels() {
            return maxChannels;
        }

        public void setMaxChannels(int maxChannels) {
            this.maxChannels = maxChannels;
        }

        public int getInitLoad() {
            return initLoad;
        }

        public void setInitLoad(int initLoad) {
            this.initLoad = initLoad;
        }

        public int getInitVersionLoad() {
            return initVersionLoad;
        }

        public void setInitVersionLoad(int initVersionLoad) {
            this.initVersionLoad = initVersionLoad;
        }

        public int getMaxDescLen() {
            return maxDescLen;
        }

        public void setMaxDescLen(int maxDescLen) {
            this.maxDescLen = maxDescLen;
        }

        public boolean isFileValidate() {
            return fileValidate;
        }

        public void setFileValidate(boolean fileValidate) {
            this.fileValidate = fileValidate;
        }

        public String getStaleAge() {
            return staleAge;
        }

        public void setStaleAge(String staleAge) {
            this.staleAge = staleAge;
        }

        public String getCheckInterval() {
            return checkInterval;
        }

        public void setCheckInterval(String checkInterval) {
            this.checkInterval = checkInterval;
        }

        public String getDraftExpire() {
            return draftExpire;
        }

        public void setDraftExpire(String draftExpire) {
            this.draftExpire = draftExpire;
        }

        public int getUserGridPageSize() {
            return userGridPageSize;
        }

        public void setUserGridPageSize(int userGridPageSize) {
            this.userGridPageSize = userGridPageSize;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.users")
    public static class HangarUserConfig {
        private int starsPerPage = 5;
        private int maxTaglineLen = 100;
        private int authorPageSize = 25;
        private int projectPageSize = 5;

        public int getStarsPerPage() {
            return starsPerPage;
        }

        public void setStarsPerPage(int starsPerPage) {
            this.starsPerPage = starsPerPage;
        }

        public int getMaxTaglineLen() {
            return maxTaglineLen;
        }

        public void setMaxTaglineLen(int maxTaglineLen) {
            this.maxTaglineLen = maxTaglineLen;
        }

        public int getAuthorPageSize() {
            return authorPageSize;
        }

        public void setAuthorPageSize(int authorPageSize) {
            this.authorPageSize = authorPageSize;
        }

        public int getProjectPageSize() {
            return projectPageSize;
        }

        public void setProjectPageSize(int projectPageSize) {
            this.projectPageSize = projectPageSize;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.orgs")
    public static class HangarOrgConfig {
        private boolean enabled = true;
        private String dummyEmailDomain = "org.papermc.io";
        private int createLimit = 5;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getDummyEmailDomain() {
            return dummyEmailDomain;
        }

        public void setDummyEmailDomain(String dummyEmailDomain) {
            this.dummyEmailDomain = dummyEmailDomain;
        }

        public int getCreateLimit() {
            return createLimit;
        }

        public void setCreateLimit(int createLimit) {
            this.createLimit = createLimit;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.queue")
    public static class HangarQueueConfig {
        private String maxReviewTime = "1d";

        public String getMaxReviewTime() {
            return maxReviewTime;
        }

        public void setMaxReviewTime(String maxReviewTime) {
            this.maxReviewTime = maxReviewTime;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.api")
    public static class HangarApiConfig {

        @NestedConfigurationProperty
        public Session session;

        @Autowired
        public HangarApiConfig(Session session) {
            this.session = session;
        }

        @Component
        @ConfigurationProperties(prefix = "hangar.api.session")
        public static class Session {
            private String publicExpiration = "3h";
            @DurationUnit(ChronoUnit.DAYS)
            private Duration expiration = Duration.ofDays(14);
            private String checkInterval = "5m";

            public String getPublicExpiration() {
                return publicExpiration;
            }

            public void setPublicExpiration(String publicExpiration) {
                this.publicExpiration = publicExpiration;
            }

            public Duration getExpiration() {
                return expiration;
            }

            public void setExpiration(Duration expiration) {
                this.expiration = expiration;
            }

            public String getCheckInterval() {
                return checkInterval;
            }

            public void setCheckInterval(String checkInterval) {
                this.checkInterval = checkInterval;
            }
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.sso")
    public static class HangarSsoConfig {

        private boolean enabled = true;
        private String loginUrl = "/sso/";
        private String signupUrl = "/sso/signup/";
        private String verifyUrl = "/sso/sudo/";
        private String logoutUrl = "/accounts/logout/";
        private String avatarUrl = "/avatar/%s?size=120x120";
        private String secret = "changeme";
        private String apiKey = "changeme";
        private Duration timeout = Duration.ofSeconds(2);
        private Duration reset = Duration.ofMinutes(10);

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getSignupUrl() {
            return signupUrl;
        }

        public void setSignupUrl(String signupUrl) {
            this.signupUrl = signupUrl;
        }

        public String getVerifyUrl() {
            return verifyUrl;
        }

        public void setVerifyUrl(String verifyUrl) {
            this.verifyUrl = verifyUrl;
        }

        public String getLogoutUrl() {
            return logoutUrl;
        }

        public void setLogoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }

        public Duration getReset() {
            return reset;
        }

        public void setReset(Duration reset) {
            this.reset = reset;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "hangar.security")
    public static class HangarSecurityConfig {

        private boolean secure = false;
        private long unsafeDownloadMaxAge = 600000;
        @NestedConfigurationProperty
        public SecurityApiConfig api;

        @Autowired
        public HangarSecurityConfig(SecurityApiConfig api) {
            this.api = api;
        }

        @Component
        @ConfigurationProperties(prefix = "hangar.security.api")
        public static class SecurityApiConfig {

            private String url = "http://localhost:8000";
            private String avatarUrl = url = "/avatar/%s?size=120x120";
            private String key = "changeme";
            private long timeout = 10000;
            @NestedConfigurationProperty
            public ApiBreakerConfig breaker;

            @Autowired
            public SecurityApiConfig(ApiBreakerConfig breaker) {
                this.breaker = breaker;
            }

            @Component
            @ConfigurationProperties(prefix = "hangar.security.api.breaker")
            public static class ApiBreakerConfig {

                private int maxFailures = 5;
                private String timeout = "10s";
                private String reset = "5m";

                public int getMaxFailures() {
                    return maxFailures;
                }

                public void setMaxFailures(int maxFailures) {
                    this.maxFailures = maxFailures;
                }

                public String getTimeout() {
                    return timeout;
                }

                public void setTimeout(String timeout) {
                    this.timeout = timeout;
                }

                public String getReset() {
                    return reset;
                }

                public void setReset(String reset) {
                    this.reset = reset;
                }
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public long getTimeout() {
                return timeout;
            }

            public void setTimeout(long timeout) {
                this.timeout = timeout;
            }

            public ApiBreakerConfig getBreaker() {
                return breaker;
            }

            public void setBreaker(ApiBreakerConfig breaker) {
                this.breaker = breaker;
            }
        }

        public boolean isSecure() {
            return secure;
        }

        public void setSecure(boolean secure) {
            this.secure = secure;
        }

        public long getUnsafeDownloadMaxAge() {
            return unsafeDownloadMaxAge;
        }

        public void setUnsafeDownloadMaxAge(long unsafeDownloadMaxAge) {
            this.unsafeDownloadMaxAge = unsafeDownloadMaxAge;
        }

        public SecurityApiConfig getApi() {
            return api;
        }

        public void setApi(SecurityApiConfig api) {
            this.api = api;
        }
    }

    @Value("${pluginUploadDir:/work/uploads}")
    private String pluginUploadDir;

    public String getPluginUploadDir() {
        return pluginUploadDir;
    }

    public void checkDebug() {
        if (!debug) {
            throw new UnsupportedOperationException("this function is supported in debug mode only");
        }
    }

    public boolean isValidProjectName(String name) {
        String sanitized = StringUtils.compact(name);
        return sanitized.length() >= 1 && sanitized.length() <= projects.maxNameLen;
    }

    // Added to make freemarker realize they are here
    public FakeUserConfig getFakeUser() {
        return fakeUser;
    }

    public HangarHomepageConfig getHomepage() {
        return homepage;
    }

    public HangarChannelsConfig getChannels() {
        return channels;
    }

    public HangarPagesConfig getPages() {
        return pages;
    }

    public HangarProjectsConfig getProjects() {
        return projects;
    }

    public HangarUserConfig getUser() {
        return user;
    }

    public HangarOrgConfig getOrg() {
        return org;
    }

    public HangarApiConfig getApi() {
        return api;
    }

    public HangarSsoConfig getSso() {
        return sso;
    }
    public HangarSecurityConfig getSecurity() {
        return security;
    }

    public HangarQueueConfig getQueue() {
        return queue;
    }
}
