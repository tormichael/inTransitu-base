/*
	Select words from dictionary FROM param1=wrdDicId  TO param2=wrdDicId
	
	author: M.Tor
	date last modified: 15.06.2016
*/

SELECT
	--[DS].[dicName] AS [DictionarySource]
	[WS].[wrdValue] AS WordSource
	--,[DT].[dicName] AS DictionaryTarget
	,[WT].[wrdValue] AS WordTarget
	,[trnTrans] AS Transcription
FROM
	itTranslate 
		INNER JOIN itWord WS ON trnSrc=WS.wrdId
		INNER JOIN itWord WT ON trnTgt=WT.wrdId		
--		INNER JOIN itDictionary DS ON WS.wrdDicId=DS.dicId
--		INNER JOIN itDictionary DT ON WT.wrdDicId=DT.dicId
WHERE
	WS.wrdDicId=2
	AND WT.wrdDicId=1
