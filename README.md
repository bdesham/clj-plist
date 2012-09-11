# clj-plist

This is a Clojure library to parse the Property List (.plist) files that are nearly ubiquitous on Mac OS X.

## Usage

The library has one public function, `parse-plist`, which takes as input a File, an InputStream, or a String naming a URI to read for the plist data.  (More exactly, `parse-plist` just passes its argument to `clojure.xml/parse`, so any source usable with that function will work with `parse-plist`.)  The function returns a native Clojure data structure corresponding to the plist data, according to the following table:

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

* Trying to parse a very large property list can cause a heap overflow, presumably due to my poor understanding of recursion.  I’m looking into it.
* Binary plist files are not (yet?) supported.  In the meantime, you can use `plutil` on OS X to convert binary plist files to XML plist files via `/usr/bin/plutil -convert xml1 -o output.plist input.plist`.

## References

For more information on plist files, see the [Apple man page for property list files](http://developer.apple.com/library/mac/#documentation/Darwin/Reference/ManPages/man5/plist.5.html).

## License

Copyright © 2011–2012 Benjamin D. Esham (www.bdesham.info).

This project is distributed under the Eclipse Public License, the same as that used by Clojure. A copy of the license is included as “epl-v10.html” in this distribution.
