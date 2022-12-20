package jw.fluent.plugin.implementation.modules.updater.github;

import java.util.Date;
import java.util.List;

//https://api.github.com/repos/jwdeveloper/JW_Piano/releases
public class GithubResponse {
    class Release {
        String url;
        String assetsUrl;
        String uploadUrl;
        String htmlUrl;
        long id;
        Author author;
        String nodeId;
        String tagName;
        String targetCommitish;
        String name;
        boolean draft;
        boolean prerelease;
        Date createdAt;
        Date publishedAt;
        List<Asset> assets;
    }

    class Author {
        String login;
        long id;
        String nodeId;
        String avatarUrl;
        String gravatarId;
        String url;
        String htmlUrl;
        String followersUrl;
        String followingUrl;
        String gistsUrl;
        String starredUrl;
        String subscriptionsUrl;
        String organizationsUrl;
        String reposUrl;
        String eventsUrl;
        String receivedEventsUrl;
        String type;
        boolean siteAdmin;
    }

    class Asset {
        String url;
        long id;
        String nodeId;
        String name;
        String label;
        User uploader;
    }

    class User {
        String login;
        long id;
        String nodeId;
        String avatarUrl;
        String gravatarId;
        String url;
        String htmlUrl;
        String followersUrl;
        String followingUrl;
        String gistsUrl;
        String starredUrl;
        String subscriptionsUrl;
        String organizationsUrl;
        String reposUrl;
        String eventsUrl;
        String receivedEventsUrl;
        String type;
        boolean siteAdmin;
    }
}
