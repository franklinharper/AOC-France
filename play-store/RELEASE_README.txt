Creating a new release
----------------------
0. Git Preparation
    * git checkout master
    * git status; to check if any changes haven't been committed.
    * git pull
1. Change Version Info in AndroidManifest.xml
    increment android:versionCode
    update android:versionName
2. Compile release APK file
    * ant clean release
        * type password for wineapps.keystore
        * just hit enter for alias 'franceaoc'
3. Perform release checks APK
    * check APK size (should be about 1.9M)
        * ls -lh bin/franceaoc-release.apk
    * install new version on device
        * adb -d install -r bin/franceaoc-release.apk
    * perform manual tests
        * Display Map
            * check that map and POIs are displayed
        * Display list of Nearby AOCs
            * check that names and distances are correct
            * click on a town and check that map is displayed
            * click on a POI and check that popup is displayed
            * click on popup and check that the town and AOCs are correct
        * Display AR
            * check that AOCs can be seen (use fake GPS if necessary)
        * Display About
            * check version number displayed
    * run Monkey tests
        * make sur phone is not on lock screen before starting the monkey!
        * adb shell monkey -p com.franceaoc.app -v 5000
4. Commit, and push version number changes (see step 1.)
    * git add AndroidManifest.xml
    * git commit -m "Change version to 1.0.x"
5. Commit release apk file
    * mv bin/franceaoc-release.apk releases/franceaoc-x.y.z.apk
    * git add releases/franceaoc-x.y.z.apk
    * git commit -m "APK version x.y.z"
6. Update Play Store files in play-store folder
    * Recent changes
    * Description
    * ...
7. Publish in Marketplace
    https://play.google.com/apps/publish
    Upload latest versions
    * App from releases/franceaoc-x.y.z.apk
    * screenshots
    * high res. application icon
    * Promotional Graphic
    * Feature Grahic
    * Description
    * Recent changes
8. Commit Create and Push Tag for new release
    * git commit
    * git tag -a v1.0.x -m 'version 1.0.x'
    * git push --tags

