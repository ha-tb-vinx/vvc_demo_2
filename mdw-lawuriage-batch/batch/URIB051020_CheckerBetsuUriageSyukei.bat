REM @echo off
REM ============================================================================
REM タイトル	：チェッカー別売上集計処理
REM 説明	：「チェッカー精算売上ワーク」を以下の条件でデータを抽出し、「店別違算精算データ」に挿入する。
REM 		  売上金額整合性チェック結果を「店別精算状況データ」に反映する。
REM version 1.00 2009/03/03 L.Cheng:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== チェッカー別売上集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB051020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
