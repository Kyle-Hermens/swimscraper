# swim-scraper

A simple script utilizing [etaoin](https://github.com/igrishaev/etaoin) that registers the user for a swim lane
as part of a regularly scheduled cron job. The implementation is specific to a single website, and said website shall
remain anonymous to avoid inundating the site with scripted sign-ups.
## Installation

In order to build this repository, you must have [Leiningen](https://leiningen.org/) and the Java SDK installed on your 
machine. You should also have [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/downloads) installed. Leiningen must also be in your path. Once you have installed both of these:

*  ```git clone https://github.com/Kyle-Hermens/swimscraper.git```
* ```cd swimscraper```
* ```lein install```
* ```lein uberjar```

This will build a standalone jar in the target directory.

## Usage
This project relies on the Clojure [config](https://github.com/yogthos/config) library. The
library specifies the precedence for various configuration sources, but the easiest route for the consumer of the jar
file is placing a ```config.edn``` file in the directory where the jar should run.

The config file should specify the following keys:

```
{:swim-name "username here"
 :swim-pass "password here"
 :swim-url "the url of the anonymous website"
 :swim-time "the preferred timeslot for the swim lane"
 :swim-activity "the activity name of the swim lane" ; This technically means you could figure out
 }
```


Once this file is in place, the script can be run via:

```java -jar swim-scraper-0.1.0-standalone.jar```

Naturally, the script won't truly work unless you've managed to divine the website it is intended for. In which case,
I praise your deductive and/or psychic abilities. Please don't steal my credit card information.

## License

Copyright © 2020 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
