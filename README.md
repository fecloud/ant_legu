# ant_legu
此项目是ant插件,方便apk 使用乐固加固

使用方法：

```xml
<target name="legu">
	    <property name="in.apk" value="${out.dir}/${ant.project.name}-release.apk" />
		  <property name="out.apk" value="${out.dir}/${ant.project.name}-release_apkcrypt.apk" />
	    <legu input="${in.apk}" output="${out.apk}" cookie="D://legu_cookie.txt"/>
	</target>
```