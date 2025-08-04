REM @echo off
REM ============================================================================
REM タイトル	：店別精算データ集計処理
REM 説明	：「会計精算売上ワーク」をデータを抽出し、「店別精算データ」「店別精算売掛データ」に挿入する。
REM 		  売上金額整合性チェック結果を「店別精算状況データ」に反映する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 店別精算データ集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB041020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
