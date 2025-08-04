REM @echo off
REM ============================================================================
REM タイトル：Ａレコード返品情報カットデータ作成
REM 説明    ：AレコードOKデータより返品商品の情報をカットして、Aレコード返品カットデータを作成する。
REM version 1.00 2017/01/10 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012850
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
