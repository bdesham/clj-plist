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

Invocation example:

    (require (org.github.bdesham.clj-plist parse-plist))
    (def my-data (parse-plist (java.io.File. "MyPropertyList.plist")))

## Example

### Input plist file

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

### Parsed Clojure version

    {"Array example" [2 3.14159],
     "Boolean example" true,
     "Data example" #<byte[] [B@3ea86d12>,
     "Date example" #<DateTime 1969-07-20T02:56:00.000-05:00>,
     "String example" "This is just some uninteresting text"}

## Notes

* Trying to parse a very large property list can cause a heap overflow.  I’m looking into it.
* Binary plist files are not (yet?) supported.

## License

Copyright © 2011, Benjamin Esham.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

**The software is provided “as is”, without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.**
