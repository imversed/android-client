Imversed is a library for [Imversed](https://github.com/imversed/imversed) blockchain-network written in kotlin.

- [Communication](#communication)
- [Configuration](#configuration)
- [License](#license)

## Communication
- If you **found a bug**, open an issue here on GitHub. The more detail the better!
- If you **have a feature request**, open an issue.

## Configuration

```
repositories {
    maven {
        url "http://office.fulldive.com:8083"
        allowInsecureProtocol = true
    }
}

dependencies {
   implementation 'com.imversed:client:1.0.2'
}
```

## License
Imversed Android client is released under the MIT license. See LICENSE for details.
