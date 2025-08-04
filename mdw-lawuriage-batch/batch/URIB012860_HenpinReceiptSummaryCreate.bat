REM @echo off
REM ============================================================================
REM タイトル：返品レシート集計データ作成
REM 説明    ：AレコードOKワークから返品レシートを抽出して
REM           店別計上日チェッカー別のレシート枚数/金額を集計したデータを作成
REM version 1.00 2017/01/25 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012860
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
