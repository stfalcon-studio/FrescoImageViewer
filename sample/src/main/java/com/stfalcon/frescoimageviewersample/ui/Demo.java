package com.stfalcon.frescoimageviewersample.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by troy379 on 14.09.16.
 */
public final class Demo {
    private Demo() {
        throw new AssertionError();
    }

    public static String[] getPosters() {
        return new String[]{
                "https://pp.vk.me/c630619/v630619423/4637a/vAOodrqPzQM.jpg", // Vincent
                "https://pp.vk.me/c630619/v630619423/46395/71QKIPW6BWM.jpg", // Jules
                "https://pp.vk.me/c630619/v630619423/46383/GOTf1IvHKoc.jpg", // Korben
                "https://pp.vk.me/c630619/v630619423/4638c/i1URx2fWj20.jpg", // Toretto
                "https://pp.vk.me/c630619/v630619423/4639e/BPoHv4xEikA.jpg", // Marty
                "https://pp.vk.me/c630619/v630619423/463a7/9EjA0oqA_yQ.jpg", // Driver
                "https://pp.vk.me/c630619/v630619423/463b0/VLPAZQJ0kuI.jpg", // Frank
                "https://pp.vk.me/c630619/v630619423/463b9/O3-hk8kIvdY.jpg", // Max
                "https://pp.vk.me/c630619/v630619423/463c2/WgtvE0FQwVY.jpg"  // Daniel
        };
    }

    public static String[] getDescriptions() {
        return new String[]{
                "Vincent Vega is a hitman and associate of Marsellus Wallace. He had a brother named Vic Vega who was shot and killed by an undercover cop while on a job. He worked in Amsterdam for over three years and recently returned to Los Angeles, where he has been partnered with Jules Winnfield.",
                "Jules Winnfield - initially he is a Hitman working alongside Vincent Vega but after revelation, or as he refers to it \"a moment of clarity\" he decides to leave to \"Walk the Earth.\" During the film he is stated to be from Inglewood, California",
                "Korben Dallas. A post-America taxi driver in New York City with a grand military background simply lives his life day to day, that is, before he meets Leeloo. Leeloo captures his heart soon after crashing into his taxi cab one day after escaping from a government-run laboratory. Korben soon finds himself running from the authorities in order to protect Leeloo, as well as becoming the center of a desperate ploy to save the world from an unknown evil.",
                "Dominic \"Dom\" Toretto is the brother of Mia Toretto, uncle to Jack and husband to Letty Ortiz. The protagonist in The Fast and the Furious franchise, Dominic is an elite street racer and auto mechanic.",
                "Martin Seamus \"Marty\" McFly Sr. - he is the world's second time traveler, the first to travel backwards in time and the first human to travel though time. He was also a high school student at Hill Valley High School in 1985. He is best friends with Dr. Emmett Brown, who unveiled his first working invention to him.",
                "The Driver - real name unknown - is a quiet man who has made a career out of stealing fast cars and using them as getaway vehicles in big-time robberies all over Los Angeles. Hot on the Driver's trail is the Detective (Bruce Dern), a conceited (and similarly nameless) cop who refers to the Driver as \"Cowboy\".",
                "Frank Martin (Transporter) - he initially serves as a reluctant hero. He is portrayed as a former Special Forces operative who was a team leader of a search and destroy unit. His military background includes operations \"in and out of\" Lebanon, Syria and Sudan. He retires from this after becoming fatigued and disenchanted with his superior officers.",
                "Maximillian \"Max\" Rockatansky started his apocalyptic adventure as a Main Force Patrol officer who fought for peace on the decaying roads of Australian civilization. Max served as the last line of defense against the reckless marauders terrorizing the roadways, driving a V8 Interceptor.",
                "Daniel Morales - the fastest delivery man for the local pizza parlor Pizza Joe in Marseille, France. On the last day of work, he sets a new speed record, then leaves the job to pursue a new career as a taxi driver with the blessings of his boss and co-workers. Daniel's vehicle is a white 1997 Peugeot 406..."
        };
    }

    public static List<CustomImage> getCustomImages() {
        String[] posters = getPosters();
        String[] descriptions = getDescriptions();
        List<CustomImage> images = new ArrayList<>();

        for (int i = 0; i < posters.length; i++) {
            images.add(new CustomImage(posters[i], descriptions[i]));
        }

        return images;
    }

    public static class CustomImage {

        private String url;
        private String description;

        public CustomImage(String url, String description) {
            this.url = url;
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }
}
