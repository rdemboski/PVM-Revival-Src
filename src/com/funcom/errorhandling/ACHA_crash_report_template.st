<?xml version="1.0" encoding="UTF-8"?>
<BugReport>

    <UniqueID>$model.uniqueId$</UniqueID>
    <UserName>$model.username$</UserName>
    <Type>$model.type$</Type>
	<Project>$model.project$</Project>
    <Email>$model.email$</Email>
    <Category>$model.category$</Category>
    <Universe>$model.universe$</Universe>

    <Body>
        $model.bodyTags: {<$it.name$>$it.value$</$it.name$>}; separator="\n"$
    </Body>

    <Attachments>
      $model.attachments: {<$it.name$><![CDATA[$it.base64Value$]]></$it.name$>}; separator="\n"$
    </Attachments>

</BugReport>