REM @echo off
REM ============================================================================
REM タイトル：日別客数集計Arecデータ作成
REM 説明    ：Aレコード、商品マスタから日別客数集計Arecデータを作成する。
REM version 1.00 2016/12/21 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012840
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
