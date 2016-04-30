# clj-plist

This is a Clojure library to parse the Property List (.plist) files that are ubiquitous on Mac OS X.

## Installation

Add the following to the dependency list in your `project.clj`:

    [com.github.bdesham/clj-plist "0.10.0"]

See [the Clojars page][Clojars] for instructions for Gradle and Maven.

[Clojars]: https://clojars.org/com.github.bdesham/clj-plist

## Usage

The library has one public function, `parse-plist`, which takes as input a File, an InputStream, or a String naming a URI to read for the plist data.  (`parse-plist` just passes its argument to `clojure.xml/parse`, so any source usable with that function will work with `parse-plist`.)  The function returns a native Clojure data structure corresponding to the plist data, according to the following table:

<table>
	<tr>
		<th>plist tag</th>
		<th>Clojure equivalent</th>
	</tr>
	<tr><td>array</td><td>vector</td></tr>
	<tr><td>data</td><td>byte[]</td></tr>
	<tr><td>date</td><td>Joda DateTime object</td></tr>
	<tr><td>dict</td><td>hash map</td></tr>
	<tr><td>false</td><td>false</td></tr>
	<tr><td>integer</td><td>Long</td></tr>
	<tr><td>real</td><td>Double</td></tr>
	<tr><td>string</td><td>string</td></tr>
	<tr><td>true</td><td>true</td></tr>
</table>

### Invocation example

```clojure
(use 'com.github.bdesham.clj-plist)
(parse-plist (java.io.File. "MyPropertyList.plist"))
```

## Example

### Input plist file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
	<key>String example</key>
	<string>This is just some uninteresting text</string>
	<key>Array example</key>
	<array>
		<integer>2</integer>
		<real>3.14159</real>
	</array>
	<key>Boolean example</key>
	<true/>
	<key>Date example</key>
	<date>1969-07-20T07:56:00Z</date>
	<key>Data example</key>
	<data>YWJjZGVmZw==</data>
</dict>
</plist>
```

### Parsed Clojure version

```clojure
{"Array example" [2 3.14159],
 "Boolean example" true,
 "Data example" #<byte[] [B@3ea86d12>,
 "Date example" #<DateTime 1969-07-20T02:56:00.000-05:00>,
 "String example" "This is just some uninteresting text"}
```

## Notes

The entire plist is sucked into memory at once, so there’s a relatively low limit on the size of the plist that can be loaded. (Trying to import my “iTunes Music Library.xml”, which is 16.2 MiB, causes a heap overflow on my system with the default Java memory limits.) Some sort of lazy loading would fix this.

Binary plist files are not supported (see [issue 1]).  In the meantime, you can use `plutil` on OS X to convert binary plist files to XML plist files via `/usr/bin/plutil -convert xml1 -o output.plist input.plist`.

[issue 1]: https://github.com/bdesham/clj-plist/issues/1

## References

For more information on plist files, see the [Apple man page for property list files](https://developer.apple.com/library/mac/documentation/Darwin/Reference/ManPages/man5/plist.5.html).

## Author

This library was written by [Benjamin Esham](https://esham.io).

This project is [hosted on GitHub](https://github.com/bdesham/clj-plist). Please feel free to submit pull requests.

## Version history

* 0.10.0 (2016-01-15)
    * Allow the specification of a `keyword-fn` function to be applied to `<key>` elements (thanks [Ben Cook]!)
    * Add a test (thanks [Marc O’Morain]!)
    * Add support for CircleCI (thanks [Marc O’Morain]!)
    * Clojure 1.5 is now required.
* 0.9.1 (2012-09-11)
    * Initial public release.

[Ben Cook]: https://github.com/blx
[Marc O’Morain]: https://github.com/marcomorain

## License

Copyright © 2012, 2016 Benjamin D. Esham. This project is distributed under the Eclipse Public License, the same as that used by Clojure. A copy of the license is included as “epl-v10.html” in this distribution.
